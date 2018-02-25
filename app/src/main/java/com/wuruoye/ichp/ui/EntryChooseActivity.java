package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
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
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.adapter.EntrySearchRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Entry;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/11.
 * this file is to
 */

public class EntryChooseActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private RecyclerView rvChoose;
    private SearchView sv;
    private RecyclerView rvSearch;
    private Button btnCreate;

    private List<Entry> mEntryList;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_choose;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mEntryList = bundle.getParcelableArrayList("entry");
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.ib_tb_back);
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
        Toast.makeText(this, "query " + query, Toast.LENGTH_SHORT).show();
    }

    private void onChooseLongClick(final Entry entry) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvChoose.getAdapter();
                        adapter.removeData(entry);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entry_choose_create:
                break;
            case R.id.ib_tb_back:
                onBackPressed();
                break;
        }
    }
}
