package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class PersonInfoActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageButton ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civAvatar;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvIntro;
    private TextView tvPwd;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_person_info;
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
        tvName = findViewById(R.id.tv_person_info_name);
        tvSex = findViewById(R.id.tv_person_info_sex);
        tvIntro = findViewById(R.id.tv_person_info_intro);
        tvPwd = findViewById(R.id.tv_person_info_pwd);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("个人信息");
        tvManager.setVisibility(View.GONE);
    }
}
