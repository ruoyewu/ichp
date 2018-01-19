package com.wuruoye.ichp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.presenter.IPresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/1/19.
 * this file is to
 */

public class MainActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {

    }

    @NotNull
    @Override
    public IPresenter getPresenter() {
        return null;
    }

    @Override
    public void onResultWorn(@NotNull final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
