package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.UserLoginContract;
import com.wuruoye.ichp.ui.presenter.pro.UserLoginPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/3/25.
 * @Description : 用户登录界面
 */

public class UserLoginActivity extends WBaseActivity<UserLoginPresenter>
        implements UserLoginContract.View, View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private EditText etName;
    private EditText etPwd;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new UserLoginPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        etName = findViewById(R.id.et_user_login_name);
        etPwd = findViewById(R.id.et_user_login_pwd);
        btnLogin = findViewById(R.id.btn_user_login_login);
        btnRegister = findViewById(R.id.btn_user_login_register);

        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("登录");
        tvManager.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onLoginResult(boolean result, String token) {
        if (!result) {
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        }else {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.btn_user_login_login:
                login();
                break;
            case R.id.btn_user_login_register:
                register();
                break;
        }
    }

    private void login() {
        String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        }else {
            mPresenter.requestLogin(name, pwd);
        }
    }

    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
