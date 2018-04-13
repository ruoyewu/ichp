package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.SettingContract;
import com.wuruoye.ichp.ui.presenter.pro.SettingPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 20:49.
 * @Description : 「设置页面」
 */

public class SettingActivity extends WBaseActivity<SettingContract.Presenter>
        implements SettingContract.View, View.OnClickListener {
    public final int SETTING_LOGIN = hashCode() % 10000;

    public static final String TEXT_LOGIN = "点击登录";
    public static final String TEXT_LOGOUT = "点击注销";

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private TextView tvLog;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new SettingPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        tvLog = findViewById(R.id.tv_setting_log);

        initLayout();

        changeLogTV(mPresenter.isLogin());
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("设置");
        tvManager.setVisibility(View.INVISIBLE);
        tvLog.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING_LOGIN && resultCode == RESULT_OK) {
            changeLogTV(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeLogTV(boolean isLogin) {
        tvLog.setTag(isLogin);
        if (isLogin) {
            tvLog.setText(TEXT_LOGOUT);
        }else {
            tvLog.setText(TEXT_LOGIN);
        }
    }

    @Override
    public void onResultLogout(boolean result, String info) {
        changeLogTV(false);
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting_log:
                boolean isLogin = (boolean) view.getTag();
                if (isLogin) {
                    mPresenter.requestLogout();
                }else {
                    startActivityForResult(new Intent(this, UserLoginActivity.class),
                            SETTING_LOGIN);
                }
                break;
            case R.id.iv_course_back:
                onBackPressed();
                break;
        }
    }
}
