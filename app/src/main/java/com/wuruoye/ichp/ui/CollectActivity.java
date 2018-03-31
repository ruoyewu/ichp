package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.adapter.OnPageChangeListenerAdapter;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.util.IManagerView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class CollectActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener {
    public static final String[] ITEM_TITLE = { "记录", "活动", "词条" };

    private Toolbar toolbar;
    private ImageView ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private TabLayout tl;
    private ViewPager vp;
    private PopupMenu pm;

    private List<IManagerView> mManageViewList;
    private int mCurrentPager = 0;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_collect;
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
        initDialog();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("我的收藏");
        tvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_person_collect_remove:
                mManageViewList.get(mCurrentPager).remove();
                return true;
            case R.id.menu_person_collect_remove_all:
                mManageViewList.get(mCurrentPager).removeAll();
                return true;
        }
        return false;
    }

    private void initViewPager() {
        mManageViewList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mUser);
            bundle.putInt("type", i);
            CollectListFragment fragment = new CollectListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
            mManageViewList.add(fragment);
        }
        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);

        vp.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPager = position;
            }
        });
    }

    private void initDialog() {
        pm = new PopupMenu(this, tvManager);
        pm.inflate(R.menu.collect_menu);
        pm.setOnMenuItemClickListener(this);
    }
}
