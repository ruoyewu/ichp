package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NoteCommentRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Entry;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class NoteShowActivity extends BaseActivity implements View.OnClickListener {
    public static final String[] ITEM_BOTTOM = {"点赞", "评论", "收藏", "词条", "分享"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_like, R.drawable.ic_comment,
            R.drawable.ic_start, R.drawable.ic_entry, R.drawable.ic_share};

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civ;
    private TextView tvUser;
    private TextView tvTime;
    private ViewPager vp;
    private RecyclerView rvEntry;
    private TextView tvContent;
    private TextView tvLocation;
    private RecyclerView rvComment;
    private LinearLayout llBottom;

    @Override
    public int getContentView() {
        return R.layout.activity_note_show;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.ib_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        civ = findViewById(R.id.civ_note_show);
        tvUser = findViewById(R.id.tv_note_show_user);
        tvTime = findViewById(R.id.tv_note_show_time);
        vp = findViewById(R.id.vp_note_show);
        rvEntry = findViewById(R.id.rv_note_show_entry);
        tvContent = findViewById(R.id.tv_note_show_content);
        tvLocation = findViewById(R.id.tv_note_show_location);
        rvComment = findViewById(R.id.rv_note_show_comment);
        llBottom = findViewById(R.id.ll_note_show_bottom);

        initLayout();
        initRecyclerView();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvManager.setVisibility(View.GONE);

        for (int i = 0; i < llBottom.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) llBottom.getChildAt(i);
            ImageView iv = ll.findViewById(R.id.iv_icon_text);
            TextView tv = ll.findViewById(R.id.tv_icon_text);
            iv.setImageResource(ICON_BOTTOM[i]);
            tv.setText(ITEM_BOTTOM[i]);
            final int finalI = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBottomClick(finalI);
                }
            });
        }
    }

    private void initRecyclerView() {
        initEntryRV();
        initCommentRV();
    }

    private void initEntryRV() {
        EntryChooseRVAdapter adapter = new EntryChooseRVAdapter();
        adapter.setOnItemLongClickListener(new BaseRVAdapter.OnItemLongClickListener<Entry>() {
            @Override
            public void onItemLongClick(Entry model) {
                onEntryLongClick(model);
            }
        });
        rvEntry.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this,
                R.drawable.decoration_horizontal));
        rvEntry.addItemDecoration(decoration);
    }

    private void initCommentRV() {
        NoteCommentRVAdapter adapter = new NoteCommentRVAdapter();
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this,
                R.drawable.decoration_vertical));
        rvComment.addItemDecoration(decoration);
    }

    private void onEntryLongClick(final Entry entry) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除词条？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvEntry.getAdapter();
                        adapter.removeData(entry);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_tb_back:
                onBackPressed();
                break;
        }
    }

    private void onBottomClick(int position) {
        switch (position) {
            case 0:     // 点赞
                break;
            case 1:     // 评论
                break;
            case 2:     // 收藏
                break;
            case 3:     // 词条
                break;
            case 4:     // 分享
                break;
        }
    }
}