package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.RecommendRVAdapter;
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

public class RecommendFragment extends BaseFragment implements RecommendContract.View{
    private SwipeRefreshLayout srlRecommend;
    private RecyclerView rvRecommend;

    private int mType;
    private RecommendContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mType = bundle.getInt("type");

        mPresenter = new DevRecommendPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srlRecommend = view.findViewById(R.id.srl_layout);
        rvRecommend = view.findViewById(R.id.rv_layout);

        initRefreshLayout();
        initRecyclerView();
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
        RecommendRVAdapter adapter = new RecommendRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Note>() {
            @Override
            public void onItemClick(Note model) {
                RecommendFragment.this.onItemClick(model);
            }
        });
        rvRecommend.setAdapter(adapter);

        requestNoteList(false);
    }

    private void requestNoteList(boolean isAdd) {
        srlRecommend.setRefreshing(true);
        mPresenter.requestNoteList(isAdd, mType);
    }

    private void onItemClick(Note note) {
        Intent intent = new Intent(getContext(), NoteShowActivity.class);
        startActivity(intent);
//        Toast.makeText(getContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srlRecommend.setRefreshing(false);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoteListResult(List<Note> noteList, boolean isAdd) {
        srlRecommend.setRefreshing(false);
        RecommendRVAdapter adapter = (RecommendRVAdapter) rvRecommend.getAdapter();
        if (isAdd) {
            adapter.addData(noteList);
        }else {
            adapter.setData(noteList);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
