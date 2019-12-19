package com.ruizhi.nolan.ui.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ruizhi.nolan.R;
import com.ruizhi.nolan.base.BaseActivity;
import com.ruizhi.nolan.base.BaseViewPagerAdapter;
import com.ruizhi.nolan.databinding.ActMainBinding;
import com.ruizhi.nolan.ui.fragment.NearbyFragment;
import com.ruizhi.nolan.ui.fragment.RecommendFragment;

public class MainActivity extends BaseActivity {
    private ActMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.act_main);
        initController();
    }

    private void initController() {
        String []titles = {
                getString(R.string.category_recommend),
                getString(R.string.category_nearby)
        };
        BaseViewPagerAdapter pagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(),titles);
        pagerAdapter.addFragment(new RecommendFragment());
        pagerAdapter.addFragment(new NearbyFragment());

        mDataBinding.homeViewPager.setAdapter(pagerAdapter);
        mDataBinding.homeTabLayout.setupWithViewPager(mDataBinding.homeViewPager);
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }
}
