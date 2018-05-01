package com.wuruoye.ichp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wuruoye.ichp.ui.CourseFragment;
import com.wuruoye.ichp.ui.FoundFragment;
import com.wuruoye.ichp.ui.HomeFragment;
import com.wuruoye.ichp.ui.UserFragment;
import com.wuruoye.ichp.ui.UserLoginActivity;
import com.wuruoye.ichp.ui.contract.pro.MainContract;
import com.wuruoye.ichp.ui.presenter.pro.MainPresenter;
import com.wuruoye.library.ui.WBaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/19.
 * this file is to
 */

public class MainActivity extends WBaseActivity<MainPresenter> implements MainContract.View{
    public static final int MAIN_LOGIN = 101;

    public static final List<String> TITLE_LIST =
            Arrays.asList("非遗记录", "非遗课堂", "发现", "我的");
    public static final List<Integer> ICON_LIST =
            Arrays.asList(R.drawable.ic_brodcast, R.drawable.ic_course, R.drawable.ic_found,
                    R.drawable.ic_user);

    private BottomNavigationBar bnbBottomBar;

    private AlertDialog dlgLogin;

    private List<Fragment> mFragmentList;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        setPresenter(new MainPresenter());
    }

    @Override
    public void initView() {
        bnbBottomBar = findViewById(R.id.bnb_main);

        initDlg();
        initFragment();
        initBottombar();

        checkLogin();
    }

    private void initDlg() {
        dlgLogin = new AlertDialog.Builder(this)
                .setTitle("未登录")
                .setMessage("未登录用户不能使用")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this,
                                UserLoginActivity.class);
                        startActivityForResult(intent, MAIN_LOGIN);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
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

    @Override
    public void checkLogin() {
        if (mPresenter.isUserLogin()) {
            mPresenter.requestLogin(mPresenter.getUserName(), mPresenter.getUserPwd());
        }else {
            dlgLogin.show();
        }
    }

    @Override
    public void onLoginResult(boolean result, String token) {
        if (!result) {
            dlgLogin.setMessage("用户登录失败，请重新登录");
            dlgLogin.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAIN_LOGIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                recreate();
            }else if (resultCode == RESULT_CANCELED) {
                dlgLogin.show();
            }
        }
    }
}
