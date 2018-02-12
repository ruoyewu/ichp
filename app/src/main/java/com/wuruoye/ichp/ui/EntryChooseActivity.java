package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.ui.model.bean.Entry;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/11.
 * this file is to
 */

public class EntryChooseActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageButton ibBack;
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
        ibBack = findViewById(R.id.ib_tb_back);
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
    }

    private void initRecyclerView() {
        initChooseRV();
        initSearchRV();
    }

    private void initChooseRV() {

    }

    private void initSearchRV() {

    }

    private void onQuery(String query) {

    }
}
