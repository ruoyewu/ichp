package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class PasswordActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageButton ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civ;
    private EditText etOld;
    private EditText etNew;
    private EditText etNew2;
    private Button btn;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_password;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ibBack = findViewById(R.id.ib_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        civ = findViewById(R.id.civ_password);
        etOld = findViewById(R.id.et_password_old);
        etNew = findViewById(R.id.et_password_new);
        etNew2 = findViewById(R.id.et_password_new_2);
        btn = findViewById(R.id.btn_password);
    }

    private void initLayout() {
        Glide.with(this)
                .load(mUser.getImage())
                .into(civ);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChange();
            }
        });
    }

    private void onChange() {

    }
}
