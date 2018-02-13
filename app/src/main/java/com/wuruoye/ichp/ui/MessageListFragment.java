package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.MessageRVAdapter;
import com.wuruoye.ichp.ui.contract.MessageContract;
import com.wuruoye.ichp.ui.model.bean.Comment;
import com.wuruoye.ichp.ui.model.bean.Message;
import com.wuruoye.ichp.ui.model.bean.Praise;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.DevMessagePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class MessageListFragment extends BaseFragment implements MessageContract.View {
    public static final int TYPE_PRAISE = 0;
    public static final int TYPE_COMMENT = 1;
    public static final int TYPE_MESSAGE = 2;

    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private User mUser;
    private int mType;
    private MessageContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
        mType = bundle.getInt("type");

        mPresenter = new DevMessagePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);

        initLayout();
        initRecyclerView();
        requestData(false);
    }

    private void initLayout() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(false);
            }
        });
    }

    private void initRecyclerView() {
        MessageRVAdapter adapter = new MessageRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                MessageListFragment.this.onItemClick(model);
            }
        });
        adapter.setOnItemSeeClickListener(new MessageRVAdapter.OnItemSeeClickListener() {
            @Override
            public void onItemSeeClick(Object object) {
                MessageListFragment.this.onItemSeeClick(object);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ActivityCompat.getDrawable(getContext(),
                R.drawable.decoration_vertical));
        rv.addItemDecoration(decoration);
    }

    private void requestData(boolean isAdd) {
        mPresenter.requestData(mUser, mType, isAdd);
    }

    // 点击项
    private void onItemClick(Object object) {
        String toast = null;
        if (object instanceof Praise) {
             toast = ((Praise) object).getFrom();
        }else if (object instanceof Comment) {
            toast = ((Comment) object).getFrom();
        }else if (object instanceof Message) {
            Intent intent = new Intent(getContext(), MessagePersonActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (toast != null) {
            Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
        }
    }

    // 点击查看
    private void onItemSeeClick(Object object) {

    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srl.setRefreshing(false);
    }

    @Override
    public void onDataResult(List<Object> dataList, boolean isAdd) {
        srl.setRefreshing(false);
        MessageRVAdapter adapter = (MessageRVAdapter) rv.getAdapter();
        if (isAdd) {
            adapter.addData(dataList);
        }else {
            adapter.setData(dataList);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
