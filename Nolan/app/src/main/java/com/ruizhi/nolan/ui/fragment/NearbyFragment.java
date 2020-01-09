package com.ruizhi.nolan.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ruizhi.nolan.R;
import com.ruizhi.nolan.base.BaseFragment;
import com.ruizhi.nolan.config.Appconfig;
import com.ruizhi.nolan.databinding.FragNearbyBinding;
import com.ruizhi.nolan.model.PictureUploadResponse;
import com.ruizhi.nolan.util.BitmapUtil;
import com.ruizhi.nolan.util.RealPathFromUriUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NearbyFragment extends BaseFragment {
    private FragNearbyBinding mDataBinding;
    private static final int REQUEST_PICK_IMAGE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.frag_nearby, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    private void initListener() {
        mDataBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
    }

    private void checkPermissions() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    getImage();
                }
            }
        });
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(getActivity(), data.getData());
                        uploadImg(realPathFromUri);
                    } else {
                        showToast("图片损坏，请重新选择");
                    }

                    break;
            }
        }
    }

    private void uploadImg(String path) {
        if (path == null || path.isEmpty() || path.length() < 3) {
            return;
        }
        String[] arrayStr = path.split("\\.");
        String endStr = arrayStr[arrayStr.length - 1];
        if (endStr.equals("jpg") || endStr.equals("png") || endStr.equals("jpeg")) {

        } else {
            showToast("不支持的图片格式");
            return;
        }
        String base64Str = BitmapUtil.bitmapToBase64(endStr, BitmapFactory.decodeFile(path));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", "strong");
            jsonObject.put("pic_category", "通用");
            jsonObject.put("pic_desc", "Android-随便一张图片测试");
            jsonObject.put("create_time", "0");
            jsonObject.put("pic_format", endStr);
            jsonObject.put("pic_content", base64Str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Rx2AndroidNetworking.post(Appconfig.getUploadUrl())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectObservable(PictureUploadResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<PictureUploadResponse>() {
                    @Override
                    public void accept(PictureUploadResponse pictureResponse) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PictureUploadResponse>() {
                    @Override
                    public void accept(PictureUploadResponse pictureResponse) throws Exception {
                        showToast("上传成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToast("上传失败");
                        Log.e("error:", "" + throwable.getMessage());
                    }
                });
    }
}
