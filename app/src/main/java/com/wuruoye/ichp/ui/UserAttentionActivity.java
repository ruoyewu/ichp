package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.UserAttentionContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.UserAttentionPresenter;
import com.wuruoye.library.ui.WBaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.wuruoye.ichp.ui.contract.pro.UserAttentionContract.TYPE_ATTEN;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class UserAttentionActivity extends WBaseActivity<UserAttentionContract.Presenter>
        implements UserAttentionContract.View, BaseRVAdapter.OnItemClickListener<Object> {

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private int mType;

    @Override
    public int getContentView() {
        return R.layout.activity_user_attention;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");

        setPresenter(new UserAttentionPresenter());
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        srl = findViewById(R.id.srl_layout);
        rv = findViewById(R.id.rv_layout);

        initLayout();
        initRecyclerView();
        mPresenter.requestData(mType);
    }

    private void initLayout() {
        setSupportActionBar(toolbar);

        tvManager.setVisibility(View.GONE);
        if (mType == TYPE_ATTEN) {
            tvTitle.setText("我的关注");
        }else {
            tvTitle.setText("关注我的");
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void requestData() {
        srl.setRefreshing(true);
        mPresenter.requestData(mType);
    }

    @Override
    public void onItemClick(Object data) {
        if (data instanceof User) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", (User) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultData(List<User> userList) {
        srl.setRefreshing(false);
        List<Object> objList = new ArrayList<>();
        objList.addAll(userList);
        ((NormalRVAdapter) rv.getAdapter()).setData(objList);
    }
}
