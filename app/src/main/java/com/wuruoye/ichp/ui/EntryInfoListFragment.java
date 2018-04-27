package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.EntryInfoContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.EntryInfoPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class EntryInfoListFragment extends WBaseFragment<EntryInfoContract.Presenter>
        implements EntryInfoContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseRVAdapter.OnItemClickListener<Object> {
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private Entry mEntry;
    private int mType;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mEntry = bundle.getParcelable("entry");
        mType = bundle.getInt("type");

        setPresenter(new EntryInfoPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);

        initRefresh();
        initRecyclerView();

        requestDataList();
    }

    private void initRefresh() {
        srl.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void requestDataList() {
        srl.setRefreshing(true);
        mPresenter.requestData(mType, mEntry.getEntry_id());
    }

    @Override
    public void onItemClick(Object data) {
        Intent intent;
        Bundle bundle = new Bundle();
        if (data instanceof Note) {
            intent = new Intent(getContext(), NoteShowActivity.class);
            bundle.putParcelable("note", (Note) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (data instanceof Course) {
            intent = new Intent(getContext(), CourseShowActivity.class);
            bundle.putParcelable("course", (Course) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultData(List<Object> dataList) {
        srl.setRefreshing(false);
        ((NormalRVAdapter)rv.getAdapter()).setData(dataList);
    }

    @Override
    public void onRefresh() {
        requestDataList();
    }
}
