package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.adapter.EntrySearchRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.EntryChooseContract;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.presenter.pro.EntryChoosePresenter;
import com.wuruoye.library.ui.WBaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/11.
 * this file is to
 */

public class EntryChooseActivity extends WBaseActivity<EntryChooseContract.Presenter>
        implements View.OnClickListener, EntryChooseContract.View {

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private RecyclerView rvChoose;
    private SearchView sv;
    private RecyclerView rvSearch;
    private Button btnCreate;

    private ArrayList<Entry> mEntryList;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_choose;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mEntryList = bundle.getParcelableArrayList("entry");
        setPresenter(new EntryChoosePresenter());
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        rvChoose = findViewById(R.id.rv_entry_choose);
        sv = findViewById(R.id.sv_entry_choose);
        rvSearch = findViewById(R.id.rv_entry_choose_search);
        btnCreate = findViewById(R.id.btn_entry_choose_create);

        initLayout();
        initRecyclerView();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("修改词条标签");
        tvManager.setVisibility(View.GONE);
        sv.setIconified(false);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return false;
                }else {
                    onQuery(query);
                    return true;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnCreate.setOnClickListener(this);
    }

    private void initRecyclerView() {
        initChooseRV();
        initSearchRV();
    }

    private void initChooseRV() {
        EntryChooseRVAdapter adapter = new EntryChooseRVAdapter();
        adapter.setOnItemLongClickListener(new BaseRVAdapter.OnItemLongClickListener<Entry>() {
            @Override
            public void onItemLongClick(Entry model) {
                onChooseLongClick(model);
            }
        });
        adapter.setData(mEntryList);
        rvChoose.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this, R.drawable.decoration_vertical));
        rvChoose.addItemDecoration(decoration);
        rvChoose.setAdapter(adapter);
    }

    private void initSearchRV() {
        EntrySearchRVAdapter adapter = new EntrySearchRVAdapter(mEntryList);
        adapter.setOnAddClickListener(new EntrySearchRVAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(Entry entry) {
                onAddEntry(entry);
            }
        });
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this, R.drawable.decoration_vertical));
        rvSearch.addItemDecoration(decoration);
        rvSearch.setAdapter(adapter);
    }

    private void onQuery(String query) {
        mPresenter.requestEntry(query);
    }

    private void onChooseLongClick(final Entry entry) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvChoose.getAdapter();
                        adapter.removeData(entry);
                        onChangeEntry();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void onAddEntry(Entry entry) {
        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvChoose.getAdapter();
        adapter.addData(entry);
    }

    private void onChangeEntry() {
        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvChoose.getAdapter();
        EntrySearchRVAdapter searchRVAdapter = (EntrySearchRVAdapter) rvSearch.getAdapter();
        searchRVAdapter.setChooseEntry(adapter.getData());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entry_choose_create:
                Intent intent = new Intent(this, EntryAddActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_tb_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("entry", mEntryList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onResultEntry(List<Entry> entryList) {
        EntrySearchRVAdapter adapter = (EntrySearchRVAdapter) rvSearch.getAdapter();
        adapter.setData(entryList);
    }

    @Override
    public void onResultEntryError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
