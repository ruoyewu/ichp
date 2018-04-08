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
import com.wuruoye.ichp.ui.adapter.RecordRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.RecordContract;
import com.wuruoye.ichp.ui.model.Note;
import com.wuruoye.ichp.ui.presenter.pro.RecordPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class RecordFragment extends WBaseFragment<RecordContract.Presenter>
        implements RecordContract.View{
    private SwipeRefreshLayout srlRecommend;
    private RecyclerView rvRecommend;

    private int mType;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mType = bundle.getInt("type");
        setPresenter(new RecordPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        srlRecommend = view.findViewById(R.id.srl_layout);
        rvRecommend = view.findViewById(R.id.rv_layout);

        initRefreshLayout();
        initRecyclerView();

        requestNoteList(false);
    }

    private void initRefreshLayout() {
        srlRecommend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNoteList(false);
            }
        });
    }

    private void initRecyclerView() {
        rvRecommend.setLayoutManager(new LinearLayoutManager(getContext()));
        RecordRVAdapter adapter = new RecordRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Note>() {
            @Override
            public void onItemClick(Note model) {
                RecordFragment.this.onItemClick(model);
            }
        });
        rvRecommend.setAdapter(adapter);

        requestNoteList(false);
    }

    private void requestNoteList(boolean isAdd) {
        srlRecommend.setRefreshing(true);
        mPresenter.requestRecord(false, mType);
    }

    private void onItemClick(Note note) {
        Intent intent = new Intent(getContext(), NoteShowActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResultRecord(List<Note> noteList, boolean isAdd) {
        srlRecommend.setRefreshing(false);
        RecordRVAdapter adapter = (RecordRVAdapter) rvRecommend.getAdapter();
        if (isAdd) {
            adapter.addData(noteList);
        }else {
            adapter.setData(noteList);
        }
    }

    @Override
    public void onErrorRecord(String error) {
        srlRecommend.setRefreshing(false);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
