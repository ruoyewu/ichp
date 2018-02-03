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
import com.wuruoye.ichp.ui.contract.EntryInfoContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.DevEntryInfoPresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class EntryInfoListFragment extends BaseFragment implements EntryInfoContract.View{
    public static final int TYPE_NOTE = 0;
    public static final int TYPE_COURSE = 1;

    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private Entry mEntry;
    private int mType;
    private EntryInfoContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mEntry = bundle.getParcelable("entry");
        mType = bundle.getInt("type");

        mPresenter = new DevEntryInfoPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);

        initRefresh();
        initRecyclerView();

        requestDataList(false);
    }

    private void initRefresh() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestDataList(false);
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                EntryInfoListFragment.this.onItemClick(model);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void requestDataList(boolean isAdd) {
        mPresenter.requestDataList(mEntry, isAdd, mType);
    }

    private void onItemClick(Object data) {
        if (data instanceof Note) {
            Toast.makeText(getContext(), ((Note) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Course) {
            Toast.makeText(getContext(), ((Course) data).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srl.setRefreshing(false);
    }

    @Override
    public void onDataListResult(List<Object> dataList, boolean isAdd) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
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
