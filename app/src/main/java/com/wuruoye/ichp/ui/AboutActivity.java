package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/5/3 19:55.
 * @Description :
 */

public class AboutActivity extends WBaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("关于");
        tvManager.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
        }
    }
}
