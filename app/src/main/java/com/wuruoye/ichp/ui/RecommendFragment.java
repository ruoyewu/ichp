package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class RecommendFragment extends BaseFragment {
    private RecyclerView rvRecommend;

    @Override
    public int getContentView() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(@NotNull View view) {
        rvRecommend = view.findViewById(R.id.rv_recommend);
    }
}
