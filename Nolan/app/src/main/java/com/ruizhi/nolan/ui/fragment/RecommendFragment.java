package com.ruizhi.nolan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruizhi.nolan.R;
import com.ruizhi.nolan.adapter.RecommendAdapter;
import com.ruizhi.nolan.base.BaseFragment;
import com.ruizhi.nolan.config.Appconfig;
import com.ruizhi.nolan.databinding.FragRecommendBinding;
import com.ruizhi.nolan.model.PictureResponse;
import com.ruizhi.nolan.model.PictureResponseModel;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RecommendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragRecommendBinding mDataBinding;
    private List<PictureResponseModel> responses = new ArrayList<>();
    RecommendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.frag_recommend, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RecommendAdapter(responses) {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public Context getParent() {
                return getActivity();
            }
        };

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mDataBinding.operatorsRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mDataBinding.operatorsRefresh.setOnRefreshListener(this);

        // 垂直一列
        // mDataBinding.operatorsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 网格两列
        // mDataBinding.operatorsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // 瀑布流两列
        mDataBinding.operatorsRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mDataBinding.operatorsRecycler.setItemAnimator(new DefaultItemAnimator());
        // mDataBinding.operatorsRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mDataBinding.operatorsRecycler.setAdapter(adapter);

        getRecommendPics();
    }

    private void getRecommendPics() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", "strong");
            jsonObject.put("pic_category", "通用");
            jsonObject.put("page", "0");
            jsonObject.put("sum", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 网络访问
        Rx2AndroidNetworking.post(Appconfig.getAllPicsUrl())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectObservable(PictureResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<PictureResponse>() {
                    @Override
                    public void accept(PictureResponse pictureResponse) throws Exception {
                        responses.clear();
                        responses.addAll(pictureResponse.data);
                        Log.e("data sum:", responses.size() + "");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PictureResponse>() {
                    @Override
                    public void accept(PictureResponse pictureResponse) throws Exception {
                        Log.e("data sum:", responses.size() + "");
                        adapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("error:", "" + throwable.getMessage());
                    }
                });

    }

    @Override
    public void onRefresh() {
        mDataBinding.operatorsRefresh.post(new Runnable() {
            @Override
            public void run() {
                mDataBinding.operatorsRefresh.setRefreshing(false);
            }
        });
    }
}
