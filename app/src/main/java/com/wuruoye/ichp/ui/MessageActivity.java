package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class MessageActivity extends BaseActivity {
    public static final String[] ITEM_TITLES = { "点赞", "评论", "私信" };

    private Toolbar toolbar;
    private ImageView ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private TabLayout tl;
    private ViewPager vp;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ibBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        tl = findViewById(R.id.tl_layout);
        vp = findViewById(R.id.vp_layout);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("我的消息");
        tvManager.setVisibility(View.GONE);
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLES.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mUser);
            bundle.putInt("type", i);
            Fragment fragment = new MessageListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLES), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }
}
