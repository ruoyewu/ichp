package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.util.KeyBoardUtil;
import com.wuruoye.ichp.ui.adapter.MessagePersonRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Message;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class MessagePersonActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ibBack;
    private TextView tvTitle;
    private TextView tvManager;
    private RecyclerView rv;
    private EditText et;
    private TextView tvSend;

    private User mAnotherUser;
    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_message_person;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ibBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        rv = findViewById(R.id.rv_message_person);
        et = findViewById(R.id.et_message_person);
        tvSend = findViewById(R.id.tv_message_person_send);

        initLayout();
        initRecyclerView();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ibBack.setOnClickListener(this);
        tvManager.setVisibility(View.GONE);
        tvSend.setOnClickListener(this);

        rv.setOnClickListener(this);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rv.smoothScrollToPosition(0);
                }
            }
        });
    }

    private void initRecyclerView() {
        MessagePersonRVAdapter adapter = new MessagePersonRVAdapter(mUser);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                true));
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.tv_message_person_send:
                sendMessage();
                break;
            case R.id.rv_message_person:
                KeyBoardUtil.hideSoftKeyboard(et);
                break;
        }
    }

    private void sendMessage() {
        String message = et.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            Message m = new Message(mUser, mAnotherUser, message, System.currentTimeMillis());
            MessagePersonRVAdapter adapter = (MessagePersonRVAdapter) rv.getAdapter();
            adapter.addDataFirst(m);

            et.setText("");
        }
    }
}
