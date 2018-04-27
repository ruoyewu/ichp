package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.MapContract;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 21:00.
 * @Description :
 */

public class PersonTraceActivity extends WBaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_person_trace;
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

        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("我的非遗足迹");
        tvManager.setVisibility(View.INVISIBLE);

        Fragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", MapContract.TYPE_USER);
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_person_trace, mapFragment)
                .commit();
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
