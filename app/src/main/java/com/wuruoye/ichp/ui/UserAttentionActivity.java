package com.wuruoye.ichp.ui;

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
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.UserAttentionContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.DevUserAttentionPresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class UserAttentionActivity extends BaseActivity implements UserAttentionContract.View{
    public static final int TYPE_FOCUS = 1;
    public static final int TYPE_FOCUSED = 2;

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private User mUser;
    private int mType;
    private UserAttentionContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.activity_user_attention;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
        mType = bundle.getInt("type");

        mPresenter = new DevUserAttentionPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.ib_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        srl = findViewById(R.id.srl_layout);
        rv = findViewById(R.id.rv_layout);

        initLayout();
        initRecyclerView();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);

        tvManager.setVisibility(View.GONE);
        if (mType == TYPE_FOCUS) {
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
                requestData(false);
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                UserAttentionActivity.this.onItemClick(model);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void requestData(boolean isAdd) {
        mPresenter.requestData(mUser, mType, isAdd);
    }

    private void onItemClick(Object data) {
        if (data instanceof User) {
            Toast.makeText(this, ((User) data).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srl.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataResult(List<Object> userList, boolean isAdd) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        if (isAdd) {
            adapter.addData(userList);
        }else {
            adapter.setData(userList);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
