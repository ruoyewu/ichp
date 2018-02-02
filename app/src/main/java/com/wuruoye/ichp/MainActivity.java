package com.wuruoye.ichp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.ui.CourseFragment;
import com.wuruoye.ichp.ui.FoundFragment;
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
            Arrays.asList("非遗播客", "非遗课堂", "发现", "我的");
    public static final List<Integer> ICON_LIST =
            Arrays.asList(R.drawable.ic_brodcast, R.drawable.ic_course, R.drawable.ic_found,
                    R.drawable.ic_user);

    private BottomNavigationBar bnbBottomBar;

    private List<Fragment> mFragmentList;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {
        bnbBottomBar = findViewById(R.id.bnb_main);

        initFragment();
        initBottombar();
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
        mFragmentList.add(new FoundFragment());
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
}
