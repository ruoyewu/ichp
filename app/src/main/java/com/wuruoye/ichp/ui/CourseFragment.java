package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.CourseRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.CourseContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.presenter.pro.CoursePresenter;
import com.wuruoye.library.ui.WBaseFragment;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/3/31.
 * @Description : 新的「活动界面」
 */

public class CourseFragment extends WBaseFragment<CourseContract.Presenter> implements
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CourseContract.View,
        BaseRVAdapter.OnItemClickListener<Course> {
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private FloatingActionButton fab;

    @Override
    protected int getContentView() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new CoursePresenter());
    }

    @Override
    protected void initView(View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);
        fab = view.findViewById(R.id.fab_course);

        initLayout();
        initRV();

        mPresenter.requestCourse(false);
    }

    private void initLayout() {
        srl.setOnRefreshListener(this);
        fab.setOnClickListener(this);

        fab.setVisibility(mPresenter.isUserConfirm() ? View.VISIBLE : View.GONE);
    }

    private void initRV() {
        CourseRVAdapter adapter = new CourseRVAdapter();
        adapter.setOnItemClickListener(this);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestCourse(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_course:
                Intent intent = new Intent(getContext(), NoteAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", NoteAddActivity.TYPE_COURSE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultCourse(List<Course> courseList, boolean isAdd) {
        srl.setRefreshing(false);
        CourseRVAdapter adapter = (CourseRVAdapter) rv.getAdapter();
        if (isAdd) {
            adapter.addData(courseList);
        }else {
            adapter.setData(courseList);
        }
    }

    @Override
    public void onItemClick(Course model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("course", model);
        Intent intent = new Intent(getContext(), CourseShowActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
