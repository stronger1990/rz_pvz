package com.ruizhi.nolan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ruizhi.nolan.R;
import com.ruizhi.nolan.base.BaseFragment;
import com.ruizhi.nolan.databinding.FragNearbyBinding;

public class NearbyFragment extends BaseFragment {
    private FragNearbyBinding mDataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.frag_nearby, container, false);
        return mDataBinding.getRoot();
    }
}
