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
import com.wuruoye.ichp.ui.contract.pro.UserInfoListContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.UserInfoListPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class UserInfoListFragment extends WBaseFragment<UserInfoListContract.Presenter>
        implements UserInfoListContract.View, BaseRVAdapter.OnItemClickListener<Object>,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private User mUser;
    private int mType;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mUser = bundle.getParcelable("user");
        mType = bundle.getInt("type");

        setPresenter(new UserInfoListPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);

        initLayout();
        initRecyclerView();
        requestData();
    }

    private void initLayout() {
        srl.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void requestData() {
        srl.setRefreshing(true);
        mPresenter.requestData(mType, mUser.getUser_id());
    }

    @Override
    public void onItemClick(Object object) {
        Intent intent;
        Bundle bundle = new Bundle();
        if (object instanceof Note) {
            intent = new Intent(getContext(), NoteShowActivity.class);
            bundle.putParcelable("note", (Note) object);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (object instanceof Course) {
            intent = new Intent(getContext(), CourseShowActivity.class);
            bundle.putParcelable("course", (Course) object);
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
        ((NormalRVAdapter) rv.getAdapter()).setData(dataList);
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
