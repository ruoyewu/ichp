package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.SearchContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.DevSearchPresenter;
import com.wuruoye.ichp.ui.util.ISearchView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/1.
 * this file is to
 */

public class SearchListFragment extends BaseFragment implements ISearchView, SearchContract.View{
    private SwipeRefreshLayout srlSearch;
    private RecyclerView rvSearch;

    private String mQueryString;
    private int mType;
    private SearchContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mType = bundle.getInt("type");

        mPresenter = new DevSearchPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srlSearch = view.findViewById(R.id.srl_layout);
        rvSearch = view.findViewById(R.id.rv_layout);

        initRefreshView();
        initRecyclerView();
    }

    private void initRefreshView() {
        srlSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestQuery(mQueryString, false);
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                SearchListFragment.this.onItemClick(model);
            }
        });
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(adapter);
    }

    private void requestQuery(String query, boolean isAdd) {
        mPresenter.requestSearchResult(query, mType, isAdd);
    }

    private void onItemClick(Object data) {
        if (data instanceof Note) {
            Toast.makeText(getContext(), ((Note) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Course) {
            Toast.makeText(getContext(), ((Course) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Entry) {
            Toast.makeText(getContext(), ((Entry) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof User) {
            Toast.makeText(getContext(), ((User) data).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void search(final String query) {
        mQueryString = query;
        if (rvSearch == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestQuery(mQueryString, false);
                }
            }, 200);
        }else {
            requestQuery(mQueryString, false);
        }
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srlSearch.setRefreshing(false);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchResult(List<Object> resultList, boolean isAdd) {
        srlSearch.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rvSearch.getAdapter();
        if (isAdd) {
            adapter.addData(resultList);
        }else {
            adapter.setData(resultList);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
