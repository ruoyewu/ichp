package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.wuruoye.ichp.ui.model.bean.Course;
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
    private AlertDialog dlgDelete;

    private int mType;

    private List<Object> mToDeleteList = new LinkedList<>();
    private Boolean mManageClicked = false;
    private int mDelSuc = 0;
    private int mDelFai = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_person_note;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");

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
        initDlg();
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
        if (mType == PersonNoteContract.TYPE_NOTE) {
            tvTitle.setText("我的非遗记录");
        }else {
            tvTitle.setText("我的非遗活动");
        }
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

    private void initDlg() {
        dlgDelete = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setCancelable(false)
                .create();
    }

    private void requestData() {
        srl.setRefreshing(true);
        mPresenter.requestData(mType);
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
        bundle.putInt("type", mType);
        Intent intent = new Intent(this, NoteAddActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void remove() {
        changeManagerState(true);
    }

    @Override
    public void removeAll() {
        remove();
    }

    @Override
    public void submit() {
        if (mToDeleteList.size() > 0) {
            List<Integer> idList = new ArrayList<>();
            if (mType == PersonNoteContract.TYPE_NOTE) {
                for (Object n : mToDeleteList) {
                    idList.add(((Note) n).getRec_id());
                }
            }else {
                for (Object c : mToDeleteList) {
                    idList.add(((Course)c).getAct_id());
                }
            }
            mPresenter.requestRemove(idList, mType);
        }
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultData(List<Object> dataList) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        adapter.setData(dataList);
    }

    @Override
    public void onResultRemove(int id, boolean deleted) {
        if (deleted) {
            mDelSuc ++;
            NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
            List<Object> objList = adapter.getData();
            for (Object obj : objList) {
                if (obj instanceof Note && ((Note) obj).getRec_id() == id) {
                    adapter.removeData(obj);
                    mToDeleteList.remove(obj);
                    break;
                }else if (obj instanceof Course && ((Course)obj).getAct_id() == id) {
                    adapter.removeData(obj);
                    mToDeleteList.remove(obj);
                    break;
                }
            }
        }else {
            mDelFai ++;
        }

        int total = mDelSuc + mToDeleteList.size();
        String message = "删除成功 " + mDelSuc + " / " + total + "\n"
                + "删除失败 " + mDelFai+ " / " + total;
        dlgDelete.setMessage(message);

        if (mDelFai == mToDeleteList.size()) {
            dlgDelete.dismiss();
            mToDeleteList.clear();
            mDelFai = 0;
            mDelSuc = 0;
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
        }else if (model instanceof Course) {
            Intent intent = new Intent(this, CourseShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("course", (Course) model);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(Object obj, boolean checked) {
        if (checked) {
            mToDeleteList.add(obj);
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
                    submit();
                }else {
                    pm.show();
                }
                break;
        }
    }
}
