package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.CourseRVAdapter;
import com.wuruoye.ichp.ui.contract.CourseContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.presenter.DevCoursePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class CourseListFragment extends BaseFragment implements CourseContract.View {
    private SwipeRefreshLayout srlCourse;
    private RecyclerView rvCourse;

    private int type;
    private CourseContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        type = bundle.getInt("type");

        mPresenter = new DevCoursePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        srlCourse = view.findViewById(R.id.srl_layout);
        rvCourse = view.findViewById(R.id.rv_layout);

        initRefreshLayout();
        initRecyclerView();

        mPresenter.requestCourseList(false, type);
    }

    private void initRefreshLayout() {
        srlCourse.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestCourseList(false, type);
            }
        });
    }

    private void initRecyclerView() {
        rvCourse.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CourseRVAdapter adapter = new CourseRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Course>() {
            @Override
            public void onItemClick(Course model) {
                CourseListFragment.this.onItemClick(model);
            }
        });
        rvCourse.setAdapter(adapter);
    }

    private void onItemClick(Course course) {
        Toast.makeText(getContext(), course.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srlCourse.setRefreshing(false);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCourseListResult(List<Course> courseList, boolean isAdd) {
        srlCourse.setRefreshing(false);
        CourseRVAdapter adapter = (CourseRVAdapter) rvCourse.getAdapter();
        if (isAdd) {
            adapter.addData(courseList);
        }else {
            adapter.setData(courseList);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
