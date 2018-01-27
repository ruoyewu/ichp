package com.wuruoye.ichp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.ui.CourseFragment;
import com.wuruoye.ichp.ui.HomeFragment;
import com.wuruoye.ichp.ui.UserFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/19.
 * this file is to
 */

public class MainActivity extends BaseActivity {
    public static final List<String> TITLE_LIST =
            Arrays.asList("非遗播客", "非遗课堂", "我的");
    public static final List<Integer> ICON_LIST =
            Arrays.asList(R.drawable.ic_movies, R.drawable.ic_book, R.drawable.ic_user);
    public static final int USER_POSITION = 2;

    private Toolbar tlMain;
    private SearchView svSearch;
    private ImageButton ibMic;
    private BottomNavigationBar bnbBottomBar;

    private List<Fragment> mFragmentList;
    private int mCurrentFragment;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {
        tlMain = findViewById(R.id.tl_main);
        svSearch = findViewById(R.id.sv_main);
        ibMic = findViewById(R.id.ib_main_mic);
        bnbBottomBar = findViewById(R.id.bnb_main);

        initToolbar();
        initFragment();
        initBottombar();
    }

    private void initToolbar() {

    }

    private void initBottombar() {
        for (int i = 0; i < TITLE_LIST.size(); i ++) {
            bnbBottomBar.addItem(new BottomNavigationItem(ICON_LIST.get(i), TITLE_LIST.get(i)));
        }
        bnbBottomBar.initialise();

        bnbBottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                changeFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new CourseFragment());
        mFragmentList.add(new UserFragment());

        for (Fragment f : mFragmentList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main_content, f)
                    .commit();
        }
        changeFragment(0);
    }

    private void changeFragment(int position) {
        mCurrentFragment = position;
        if (position == USER_POSITION) {
            hideToolbar(true);
        }else {
            hideToolbar(false);
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i ++) {
            if (i == position) {
                transaction.show(mFragmentList.get(i));
            }else {
                transaction.hide(mFragmentList.get(i));
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideToolbar(boolean isHide) {
        if (isHide) {
            tlMain.setVisibility(View.GONE);
        }else {
            tlMain.setVisibility(View.VISIBLE);
        }
    }
}
