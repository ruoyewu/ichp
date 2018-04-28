package com.wuruoye.ichp.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.util.ShareUtil;
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NoteCommentRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.NoteShowContract;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.NoteShowPresenter;
import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.util.log.WLog;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class NoteShowActivity extends WBaseActivity<NoteShowContract.Presenter> implements
        View.OnClickListener, NoteShowContract.View{
    public static final String[] ITEM_BOTTOM = {"点赞", "收藏", "评论", "词条", "分享"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_like_white, R.drawable.ic_star_white,
            R.drawable.ic_comment, R.drawable.ic_entry, R.drawable.ic_share};

    public final int NOTE_SHOW_ENTRY = hashCode() % 10000;

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

    private AlertDialog dlgComment;
    private AlertDialog dlgEntry;
    private AlertDialog dlgError;
    private EditText etComment;
    private TextView tvPraise;
    private ImageView ivPraise;
    private TextView tvCollect;
    private ImageView ivCollect;

    private User mUser;
    private Note mNote;
    private boolean isModifyEntry;

    @Override
    public int getContentView() {
        return R.layout.activity_note_show;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mNote = bundle.getParcelable("note");
        setPresenter(new NoteShowPresenter());
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
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

        initDlg();
        initLayout();
        initVP();
        initRecyclerView();

        mPresenter.requestEntryList(mNote.getLabels_id_str());
        mPresenter.requestUserInfo(mNote.getRecorder());
        mPresenter.requestCommentList(mNote.getRec_id());
    }

    @SuppressLint("InflateParams")
    private void initDlg() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.dlg_comment, null);
        etComment = view.findViewById(R.id.et_dlg_comment);
        dlgComment = new AlertDialog.Builder(this)
                .setTitle("输入评论内容")
                .setView(view)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String comment = etComment.getText().toString();
                        mPresenter.requestComment(mNote.getRec_id(), comment);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dlgEntry = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("正在更改词条信息...")
                .create();
        dlgError = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvManager.setVisibility(View.GONE);
        civ.setOnClickListener(this);

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
            if (i == 0) {
                tvPraise = tv;
                ivPraise = iv;
            }else if (i == 1) {
                tvCollect = tv;
                ivCollect = iv;
            }
        }

        tvTitle.setText(mNote.getTitle());
        tvTime.setText(mPresenter.parseDate(mNote.getIssue_date()));
        tvContent.setText(mNote.getDiscribe());
        tvLocation.setText(mPresenter.parseLocation(mNote.getAddr())[0]);

        changePraise(mNote.isApprove());
        changeCollect(mNote.isColl());
    }

    private void initVP() {
        try {
            List<Media> mediaList = mPresenter.parseMedia(mNote.getUrl(), mNote.getType());
            WLog.loge(this, mediaList.toString());
            List<Fragment> fragmentList = new ArrayList<>();
            for (Media m : mediaList) {
                Fragment fragment = new MediaFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("media", m);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }

            vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(),
                    new ArrayList<String>(3), fragmentList));
        } catch (IllegalArgumentException e) {
            onResultError(e.getMessage());
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
        rvEntry.setAdapter(adapter);
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
        rvComment.setNestedScrollingEnabled(false);
    }

    private void changePraise(boolean praise) {
        tvPraise.setText(praise ? "已点赞" : "点赞");
        tvPraise.setTextColor(praise ? Color.BLACK : Color.WHITE);
        ivPraise.setImageResource(praise ? R.drawable.ic_like_black : R.drawable.ic_like_white);
        ivPraise.setTag(praise);
    }

    private void changeCollect(boolean collect) {
        tvCollect.setText(collect ? "已收藏" : "收藏");
        tvCollect.setTextColor(collect ? Color.BLACK : Color.WHITE);
        ivCollect.setImageResource(collect ? R.drawable.ic_star_black : R.drawable.ic_star_white);
        ivCollect.setTag(collect);
    }

    private void onEntryLongClick(final Entry entry) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除词条？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvEntry.getAdapter();
                        adapter.removeData(entry);
                        isModifyEntry = true;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void share() {
        String text = mNote.getTitle() + "\n" + mNote.getDiscribe();
        ShareUtil.INSTANCE.shareText(text, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NOTE_SHOW_ENTRY) {
                List<Entry> entryList = data.getParcelableArrayListExtra("entry");
                ((EntryChooseRVAdapter)rvEntry.getAdapter()).setData(entryList);
                isModifyEntry = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.civ_note_show:
                if (mUser != null) {
                    Intent intent = new Intent(this, UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    private void onBottomClick(int position) {
        switch (position) {
            case 0:     // 点赞
                boolean approve = !mNote.isApprove();
                mNote.setApprove(!mNote.isApprove());
                changePraise(approve);
                mPresenter.requestPraise(mNote.getRec_id(), approve);
                break;
            case 1:     // 收藏
                boolean coll = !mNote.isColl();
                mNote.setColl(coll);
                changeCollect(coll);
                mPresenter.requestCollect(mNote.getRec_id(), coll);
                break;
            case 2:     // 评论
                dlgComment.show();
                break;
            case 3:     // 词条
                Intent intent = new Intent(this, EntryChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("entry", (ArrayList<? extends Parcelable>)
                        ((EntryChooseRVAdapter)rvEntry.getAdapter()).getData());
                intent.putExtras(bundle);
                startActivityForResult(intent, NOTE_SHOW_ENTRY);
                break;
            case 4:     // 分享
                share();
                break;
        }
    }

    @Override
    public void onResultUserInfo(User user) {
        Glide.with(civ)
                .load(user.getImage_src())
                .into(civ);
        tvUser.setText(user.getAccount_name());
        mUser = user;
    }

    @Override
    public void onResultEntry(List<Entry> entryList) {
        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvEntry.getAdapter();
        adapter.addData(entryList);
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultNoteComment(List<NoteComment> commentList) {
        NoteCommentRVAdapter adapter = (NoteCommentRVAdapter) rvComment.getAdapter();
        adapter.setData(commentList);
    }

    @Override
    public void onResultUpComment(boolean result, String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        if (result) {
            etComment.getText().clear();
            mPresenter.requestCommentList(mNote.getRec_id());
        }
    }

    @Override
    public void onResultModifyEntry(boolean result, String info) {
        dlgEntry.dismiss();
        if (result) {
            finish();
        }else {
            dlgEntry.setMessage(info);
            dlgError.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isModifyEntry) {
            dlgEntry.show();
            mPresenter.requestModifyEntry(mNote.getRec_id(),
                    ((EntryChooseRVAdapter) rvEntry.getAdapter()).getData());
        }else {
            super.onBackPressed();
        }
    }
}