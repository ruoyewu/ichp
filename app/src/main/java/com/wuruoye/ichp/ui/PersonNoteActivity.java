package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.PersonNoteContract;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.DevPersonNotePresenter;
import com.wuruoye.ichp.ui.util.IManagerView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public class PersonNoteActivity extends BaseActivity
        implements PopupMenu.OnMenuItemClickListener, PersonNoteContract.View, IManagerView {
    private Toolbar toolbar;
    private ImageView ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private PopupMenu pm;

    private User mUser;
    private PersonNoteContract.Presenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.activity_person_note;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");

        mPresenter = new DevPersonNotePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ibBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        srl = findViewById(R.id.srl_layout);
        rv = findViewById(R.id.rv_layout);

        initLayout();
        initRecyclerView();
        initMenu();
        requestData(false);
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("我的非遗记录");
        tvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.show();
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(false);
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(Object model) {
                PersonNoteActivity.this.onItemClick(model);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void initMenu() {
        pm = new PopupMenu(this, tvManager);
        pm.inflate(R.menu.person_note);
        pm.setOnMenuItemClickListener(this);
    }

    private void requestData(boolean isAdd) {
        mPresenter.requestData(mUser, isAdd);
    }

    private void onItemClick(Object data) {
        if (data instanceof Note) {
            Toast.makeText(this, ((Note) data).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_note_add:
                add();
                return true;
            case R.id.person_note_remove:
                remove();
                return true;
            case R.id.person_note_remove_all:
                removeAll();
                return true;
        }
        return false;
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        srl.setRefreshing(false);
    }

    @Override
    public void onDataResult(List<Object> dataList, boolean isAdd) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        if (isAdd) {
            adapter.addData(dataList);
        }else {
            adapter.setData(dataList);
        }
    }

    @Override
    public void add() {
        Intent intent = new Intent(this, NoteAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void remove() {

    }

    @Override
    public void removeAll() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
