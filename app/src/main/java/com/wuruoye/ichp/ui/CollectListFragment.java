package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.CollectContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.DevCollectPresenter;
import com.wuruoye.ichp.ui.util.IManagerView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public class CollectListFragment extends BaseFragment
        implements CollectContract.View, IManagerView{
    public static final int TYPE_NOTE = 0;
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_ENTRY = 2;

    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private User mUser;
    private int mType;

    private CollectContract.Presenter mPresenter;
    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
        mType = bundle.getInt("type");

        mPresenter = new DevCollectPresenter();
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
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                CollectListFragment.this.onItemClick(model);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void requestData(boolean isAdd) {
        srl.setRefreshing(true);
        mPresenter.requestData(mUser, mType, isAdd);
    }

    private void onItemClick(Object data) {
        if (data instanceof Note) {
            Toast.makeText(getContext(), ((Note) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Course) {
            Toast.makeText(getContext(), ((Course) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Entry) {
            Toast.makeText(getContext(), ((Entry) data).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srl.setRefreshing(false);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataResult(List<Object> dataList, boolean isAdd) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        if (isAdd) {
            adapter.addData(dataList);
        }else {
            adapter.setData(dataList);
        }
    }

    @Override
    public void onRemoveResult(Object data) {

    }

    @Override
    public void onRemoveAllResult(List<Object> dataList) {

    }

    @Override
    public void add() {

    }

    @Override
    public void remove() {
        Toast.makeText(getContext(), "remove", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeAll() {
        Toast.makeText(getContext(), "remove all", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
