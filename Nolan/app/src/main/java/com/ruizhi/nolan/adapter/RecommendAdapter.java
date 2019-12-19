package com.ruizhi.nolan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruizhi.nolan.R;
import com.ruizhi.nolan.config.Appconfig;
import com.ruizhi.nolan.model.PictureResponseModel;

import java.util.List;

public abstract class RecommendAdapter extends BaseQuickAdapter<PictureResponseModel, BaseViewHolder> {
    public RecommendAdapter(@Nullable List<PictureResponseModel> data) {
        super(R.layout.layout_item_recommend, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, PictureResponseModel item) {
        if (item != null) {
            String path = item.pic_path;
            if (path == null || path.isEmpty()) {
                return;
            }
            Log.e("图片路径", path);
            String replaceStr = "127.0.0.1";
            if (path.startsWith(replaceStr)) {
                path = Appconfig.getBaseUrl() + path.substring(replaceStr.length());
            }
            Log.e("替换后的路径", path);
            ImageView imageView = holder.itemView.findViewById(R.id.iv_pic);
            Glide.with(getParent()).load(path).into(imageView);

            // 不人为设置两列的高度，则默认根据图片大小自适配
//            int position = holder.getAdapterPosition();
//            if (position % 2 == 0) {
//                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
//            } else {
//                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
//            }

            holder.setText(R.id.tv_desc, item.pic_desc)
                    .getConvertView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    public abstract void onItemClick(int position);

    public abstract Context getParent();
}
