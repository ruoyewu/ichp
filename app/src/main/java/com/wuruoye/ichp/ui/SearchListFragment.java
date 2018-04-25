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
import com.wuruoye.ichp.ui.contract.pro.SearchContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.SearchPresenter;
import com.wuruoye.ichp.ui.util.ISearchView;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/1.
 * this file is to
 */

public class SearchListFragment extends WBaseFragment<SearchContract.Presenter>
        implements ISearchView, SearchContract.View, BaseRVAdapter.OnItemClickListener<Object>,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout srlSearch;
    private RecyclerView rvSearch;

    private String mQueryString = "";
    private int mType;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mType = bundle.getInt("type");

        setPresenter(new SearchPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        srlSearch = view.findViewById(R.id.srl_layout);
        rvSearch = view.findViewById(R.id.rv_layout);

        initRefreshView();
        initRecyclerView();

        if (!getQuery().equals(mQueryString)) {
            search(getQuery());
        }
    }

    private void initRefreshView() {
        srlSearch.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(adapter);
    }

    private String getQuery() {
        return ((SearchActivity) getActivity()).getQuery();
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
//            Toast.makeText(getContext(), ((Note) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Course) {
            intent = new Intent(getContext(), CourseShowActivity.class);
            bundle.putParcelable("course", (Course) data);
            intent.putExtras(bundle);
            startActivity(intent);
//            Toast.makeText(getContext(), ((Course) data).getTitle(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof Entry) {
            intent = new Intent(getContext(), EntryInfoActivity.class);
            bundle.putParcelable("entry", (Entry) data);
            intent.putExtras(bundle);
            startActivity(intent);
//            Toast.makeText(getContext(), ((Entry) data).getName(), Toast.LENGTH_SHORT).show();
        }else if (data instanceof User) {
            intent = new Intent(getContext(), UserInfoActivity.class);
            bundle.putParcelable("user", (User) data);
            intent.putExtras(bundle);
            startActivity(intent);
//            Toast.makeText(getContext(), ((User) data).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void search(String query) {
        if (mPresenter != null) {
            mQueryString = query;
            mPresenter.requestData(mType, query);
        }
    }

    @Override
    public void onResultError(String error) {
        srlSearch.setRefreshing(false);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultData(List<Object> dataList) {
        srlSearch.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rvSearch.getAdapter();
        adapter.setData(dataList);
    }

    @Override
    public void onRefresh() {
        srlSearch.setRefreshing(false);
        mPresenter.requestData(mType, mQueryString);
    }
}
