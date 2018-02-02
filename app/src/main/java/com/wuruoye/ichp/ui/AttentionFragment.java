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
import com.wuruoye.ichp.ui.adapter.AttentionRVAdapter;
import com.wuruoye.ichp.ui.contract.RecommendContract;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.DevRecommendPresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class AttentionFragment extends BaseFragment implements RecommendContract.View{
    private SwipeRefreshLayout srlAttention;
    private RecyclerView rvAttention;

    private RecommendContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mPresenter = new DevRecommendPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srlAttention = view.findViewById(R.id.srl_layout);
        rvAttention = view.findViewById(R.id.rv_layout);

        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void initRefreshLayout() {
        srlAttention.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNoteList(false);
            }
        });
    }

    private void initRecyclerView() {
        AttentionRVAdapter adapter = new AttentionRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Note>() {
            @Override
            public void onItemClick(Note model) {
                AttentionFragment.this.onItemClick(model);
            }
        });
        rvAttention.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAttention.setAdapter(adapter);

        requestNoteList(false);
    }

    private void onItemClick(Note note) {
        Toast.makeText(getContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void requestNoteList(boolean isAdd) {
        srlAttention.setRefreshing(true);
        mPresenter.requestNoteList(isAdd);
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srlAttention.setRefreshing(false);
    }

    @Override
    public void onNoteListResult(List<Note> noteList, boolean isAdd) {
        srlAttention.setRefreshing(false);
        AttentionRVAdapter adapter = (AttentionRVAdapter) rvAttention.getAdapter();
        if (isAdd) {
            adapter.addData(noteList);
        }else {
            adapter.setData(noteList);
        }
    }
}
