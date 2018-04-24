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
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.PersonNoteContract;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.PersonNotePresenter;
import com.wuruoye.ichp.ui.util.IManagerView;
import com.wuruoye.library.ui.WBaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public class PersonNoteActivity extends WBaseActivity<PersonNoteContract.Presenter>
        implements PopupMenu.OnMenuItemClickListener, PersonNoteContract.View, IManagerView,
        BaseRVAdapter.OnItemClickListener<Object>,NormalRVAdapter.OnActionListener,
        View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private PopupMenu pm;

    private List<Note> mToDeleteList = new LinkedList<>();
    private Boolean mManageClicked = false;

    @Override
    public int getContentView() {
        return R.layout.activity_person_note;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        setPresenter(new PersonNotePresenter());
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
        requestData();
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
        tvManager.setOnClickListener(this);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnActionListener(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void initMenu() {
        pm = new PopupMenu(this, tvManager);
        pm.inflate(R.menu.person_note);
        pm.setOnMenuItemClickListener(this);
    }

    private void requestData() {
        mPresenter.requestData();
    }

    private void changeManagerState(boolean clicked) {
        tvManager.setText((mManageClicked = clicked) ? "完成" : "管理");
        ((NormalRVAdapter)rv.getAdapter()).setShowCheck(clicked);
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
    public void add() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", NoteAddActivity.TYPE_NOTE);
        Intent intent = new Intent(this, NoteAddActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void remove() {
        changeManagerState(true);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        changeManagerState(true);
    }

    @Override
    public void removeAll() {
        remove();
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultData(List<Note> dataList) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        List<Object> objList = new ArrayList<>();
        objList.addAll(dataList);
        adapter.setData(objList);
    }

    @Override
    public void onResultRemove(int id) {
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        List<Object> objList = adapter.getData();
        for (Object obj : objList) {
            if (obj instanceof Note && ((Note) obj).getRec_id() == id) {
                adapter.removeData(obj);
                break;
            }
        }
    }

    @Override
    public void onItemClick(Object model) {
        if (model instanceof Note) {
            Intent intent = new Intent(this, NoteShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("note", (Note)model);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(Object obj, boolean checked) {
        if (checked) {
            mToDeleteList.add((Note) obj);
        }else {
            mToDeleteList.remove(obj);
        }
    }

    @Override
    public void onBackPressed() {
        if (mManageClicked) {
            changeManagerState(false);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tb_manager:
                if (mManageClicked) {
                    changeManagerState(false);
                    if (mToDeleteList.size() > 0) {
                        List<Integer> idList = new ArrayList<>();
                        for (Note n : mToDeleteList) {
                            idList.add(n.getRec_id());
                        }
                        mPresenter.requestRemove(idList);
                    }
                }else {
                    pm.show();
                }
                break;
        }
    }
}
