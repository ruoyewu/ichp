package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.PersonInfoContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.PersonInfoPresenter;
import com.wuruoye.library.adapter.TextWatchAdapter;
import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.util.media.IWPhoto;
import com.wuruoye.library.util.media.WPhoto;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class PersonInfoActivity extends WBaseActivity<PersonInfoContract.Presenter>
        implements View.OnClickListener, IWPhoto.OnWPhotoListener<String>, PersonInfoContract.View {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civAvatar;
    private EditText etName;
    private EditText etSign;
    private EditText etPhone;
    private EditText etRealName;
    private TextView tvPwd;

    private AlertDialog dlgPhoto;
    private AlertDialog dlgUpload;
    private AlertDialog dlgConfirm;
    private WPhoto mPhotoGet;

    private User mUser;
    private String mPhotoPath;
    private String mPhotoUrl;
    private boolean isChanged = false;

    @Override
    public int getContentView() {
        return R.layout.activity_person_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
        mPhotoGet = new WPhoto(this);

        setPresenter(new PersonInfoPresenter());
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        civAvatar = findViewById(R.id.civ_person_info);
        etName = findViewById(R.id.et_person_info_name);
        etSign = findViewById(R.id.et_person_info_sign);
        etPhone = findViewById(R.id.et_person_info_phone);
        etRealName = findViewById(R.id.et_person_info_real_name);
        tvPwd = findViewById(R.id.tv_person_info_pwd);

        initDlg();
        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("个人信息");
        tvManager.setVisibility(View.INVISIBLE);
        tvManager.setOnClickListener(this);
        tvManager.setText("保存");
        etName.setText(mUser.getAccount_name());
        etName.setEnabled(false);
        etSign.setText(mUser.getSign());
        etPhone.setText(mUser.getTelephone());
        etRealName.setText(mUser.getName());
        tvPwd.setOnClickListener(this);
        civAvatar.setOnClickListener(this);

        Glide.with(civAvatar)
                .load(mUser.getImage_src())
                .into(civAvatar);

        etPhone.addTextChangedListener(new TextWatchAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                change(true);
            }
        });
        etSign.addTextChangedListener(new TextWatchAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                change(true);
            }
        });
        etRealName.addTextChangedListener(new TextWatchAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                change(true);
            }
        });
    }

    private void initDlg() {
        dlgPhoto = new AlertDialog.Builder(this)
                .setItems(new String[]{"相册", "拍摄"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                mPhotoGet.choosePhoto(PersonInfoActivity.this);
                                break;
                            case 1:
                                mPhotoGet.takePhoto("", PersonInfoActivity.this);
                                break;
                        }
                    }
                })
                .create();
        dlgUpload = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setCancelable(false)
                .create();
        dlgConfirm = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("信息已修改，是否保存？")
                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        save();
                    }
                })
                .setNegativeButton("不修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra("user", mUser);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.tv_tb_manager:
                save();
                break;
            case R.id.tv_person_info_pwd:
                Intent intent = new Intent(this, ModifyPwdActivity.class);
                break;
            case R.id.civ_person_info:
                dlgPhoto.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isChanged) {
            dlgConfirm.show();
        }else {
            Intent intent = new Intent();
            intent.putExtra("user", mUser);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void change(boolean isChanged) {
        this.isChanged = isChanged;
        tvManager.setVisibility(isChanged ? View.VISIBLE : View.INVISIBLE);
    }

    private void save() {
        dlgUpload.setMessage("正在上传图片...");
        dlgUpload.show();
        if (TextUtils.isEmpty(mPhotoPath)) {
            doSave();
        }else {
            mPresenter.requestUploadFile(mPhotoPath);
        }
    }

    private void doSave() {
        dlgUpload.setMessage("正在上传信息...");
        if (mPhotoUrl != null) {
            mUser.setImage_src(mPhotoUrl);
        }
        mUser.setSign(etSign.getText().toString());
        mUser.setTelephone(etPhone.getText().toString());
        mUser.setName(etRealName.getText().toString());

        mPresenter.requestUpload(mUser);
    }

    @Override
    public void onPhotoResult(String s) {
        change(true);
        mPhotoPath = s;
        Glide.with(civAvatar)
                .load(s)
                .into(civAvatar);
    }

    @Override
    public void onPhotoError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultError(String error) {
        dlgUpload.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUploadFile(String url) {
        mPhotoUrl = url;
        doSave();
    }

    @Override
    public void onResultUpload() {
        dlgUpload.dismiss();
        change(false);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }
}
