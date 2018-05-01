package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.ModifyPwdContract;
import com.wuruoye.ichp.ui.presenter.pro.ModifyPwdPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 13:02.
 * @Description :
 */

public class ModifyPwdActivity extends WBaseActivity<ModifyPwdContract.Presenter>
        implements View.OnClickListener, ModifyPwdContract.View {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private EditText etOld;
    private EditText etNew;
    private EditText etConfirm;
    private Button btn;

    private AlertDialog dlgTip;

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new ModifyPwdPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        etOld = findViewById(R.id.et_modify_pwd_old);
        etNew = findViewById(R.id.et_modify_pwd_new);
        etConfirm = findViewById(R.id.et_modify_pwd_confim);
        btn = findViewById(R.id.btn_modify_pwd_submit);

        initLayout();
        initDlg();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvManager.setVisibility(View.GONE);
        tvTitle.setText("修改密码");
        btn.setOnClickListener(this);
    }

    private void initDlg() {
        dlgTip = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("正在修改密码...")
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.btn_modify_pwd_submit:
                modify();
                break;
        }
    }

    private void modify() {
        String oldPwd = etOld.getText().toString();
        String newPwd = etNew.getText().toString();
        String confirmPwd = etConfirm.getText().toString();

        if (!newPwd.equals(confirmPwd)) {
            Toast.makeText(this, "密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }

        dlgTip.show();
        mPresenter.requestChange(confirmPwd);
    }

    @Override
    public void onResultError(String error) {
        dlgTip.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultModify() {
        dlgTip.dismiss();
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
