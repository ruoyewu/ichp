package com.wuruoye.ichp.ui;

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
import com.wuruoye.ichp.ui.contract.pro.RegisterContract;
import com.wuruoye.ichp.ui.presenter.pro.RegisterPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 21:46.
 * @Description :
 */

public class RegisterActivity extends WBaseActivity<RegisterContract.Presenter>
        implements View.OnClickListener,
        RegisterContract.View {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManger;
    private EditText etName;
    private EditText etPwd;
    private EditText etPwdCon;
    private Button btn;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new RegisterPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManger = findViewById(R.id.tv_tb_manager);
        etName = findViewById(R.id.et_register_name);
        etPwd = findViewById(R.id.et_register_pwd);
        etPwdCon = findViewById(R.id.et_register_pwd_con);
        btn = findViewById(R.id.btn_register);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("注册");
        tvManger.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_course_back:
                onBackPressed();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        String pwdCon = etPwdCon.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwdCon)) {
            Toast.makeText(this, "两次输入密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.requestRegister(name, pwd);
    }

    @Override
    public void onResultRegister(boolean result, String info) {
        if (result) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }
}
