package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.MediaActivity;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.base.model.Config;
import com.wuruoye.ichp.base.util.FileUtil;
import com.wuruoye.ichp.base.util.PermissionUtil;
import com.wuruoye.ichp.ui.adapter.MediaRVAdapter;
import com.wuruoye.ichp.ui.contract.AddNoteContract;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.presenter.DevAddNotePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class NoteAddActivity extends MediaActivity
        implements View.OnClickListener, AddNoteContract.View {
    public static final String[] PHOTO_ITEM = {"照片选取", "照片拍摄"};
    public static final String[] VIDEO_ITEM = {"视频选取", "视频拍摄"};
    public static final String[] RECORD_ITEM = {"音频选取", "音频拍摄"};
    public static final String[] TITLE_IT_ITEM = {"照片", "视频", "音频", "词条", "返回", "发布"};
    public static final int[] ICON_IT_ITEM = {1, 2, 3, 4, 5, 6};
    public static final int LOCATION_CODE = 1;
    public static final int TIME_RECORD_LIMIT = 100000;
    public static final int CHOOSE_ENTRY = 201;

    public static final int TYPE_NOTE = 1;
    public static final int TYPE_COURSE = 2;

    private EditText etTitle;
    private NestedScrollView nsv;
    private EditText etContent;
    private LinearLayout llEntryList;
    private TextView tvLocation;
    private LinearLayout llLocation;
    private LinearLayout llCourseInfo;
    private EditText etDate;
    private EditText etPlace;
    private EditText etEntrance;
    private RecyclerView rvMedia;
    private LinearLayout[] llList;

    private AlertDialog dlgPhoto;
    private AlertDialog dlgVideo;
    private AlertDialog dlgRecord;

    private AddNoteContract.Presenter mPresenter;
    private int mCurrentMediaType;
    private int mType;

    @Override
    public int getContentView() {
        return R.layout.activity_note_add;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");

        mPresenter = new DevAddNotePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView() {
        llList = new LinearLayout[6];
        etTitle = findViewById(R.id.et_note_add_title);
        nsv = findViewById(R.id.nsv_note_add);
        etContent = findViewById(R.id.et_note_add_content);
        llEntryList = findViewById(R.id.ll_note_add_entry_list);
        tvLocation = findViewById(R.id.tv_note_add_location);
        llLocation = findViewById(R.id.ll_note_add_location);
        llCourseInfo = findViewById(R.id.ll_note_add_course_info);
        etDate = findViewById(R.id.et_note_add_date);
        rvMedia = findViewById(R.id.rv_note_add_media);
        llList[0] = findViewById(R.id.ll_note_add_photo);
        llList[1] = findViewById(R.id.ll_note_add_video);
        llList[2] = findViewById(R.id.ll_note_add_record);
        llList[3] = findViewById(R.id.ll_note_add_entry);
        llList[4] = findViewById(R.id.ll_note_add_back);
        llList[5] = findViewById(R.id.ll_note_add_publish);

        initType();
        initLayout();
        initRecyclerView();
        initDialog();
        getLocation();
        remeasureET();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initType() {
        if (mType == TYPE_NOTE) {
            etTitle.setHint("请输入记录标题");
            etContent.setHint("请输入记录内容");
            llCourseInfo.setVisibility(View.GONE);
            llLocation.setVisibility(View.VISIBLE);
        }else if (mType == TYPE_COURSE) {
            etTitle.setHint("请输入活动标题");
            etContent.setHint("请输入活动内容");
            llCourseInfo.setVisibility(View.VISIBLE);
            llLocation.setVisibility(View.GONE);
        }
    }

    private void initLayout() {
        for (int i = 0; i < TITLE_IT_ITEM.length; i++) {
            ImageView iv = llList[i].findViewById(R.id.iv_icon_text);
            TextView tv = llList[i].findViewById(R.id.tv_icon_text);
//            iv.setImageResource(ICON_IT_ITEM[i]);
            tv.setText(TITLE_IT_ITEM[i]);
            llList[i].setOnClickListener(this);
        }
    }

    private void initRecyclerView() {
        MediaRVAdapter adapter = new MediaRVAdapter();
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Media>() {
            @Override
            public void onItemClick(Media model) {
                NoteAddActivity.this.onItemClick(model);
            }
        });
        adapter.setOnItemLongClickListener(new BaseRVAdapter.OnItemLongClickListener<Media>() {
            @Override
            public void onItemLongClick(Media model) {
                NoteAddActivity.this.onItemLongClick(model);
            }
        });
        rvMedia.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvMedia.setAdapter(adapter);
        checkRecyclerView();
    }

    private void initDialog() {
        dlgPhoto = new AlertDialog.Builder(this)
                .setItems(PHOTO_ITEM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentMediaType = 0;
                        switch (which) {
                            case 0:
                                choosePhoto();
                                break;
                            case 1:
                                takePhoto(mPresenter.generateImageName());
                                break;
                        }
                    }
                })
                .create();
        dlgVideo = new AlertDialog.Builder(this)
                .setItems(VIDEO_ITEM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentMediaType = 1;
                        switch (which) {
                            case 0:
                                chooseVideo();
                                break;
                            case 1:
                                takeVideo();
                                break;
                        }
                    }
                })
                .create();
        dlgRecord = new AlertDialog.Builder(this)
                .setItems(RECORD_ITEM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentMediaType = 2;
                        switch (which) {
                            case 0:
                                chooseRecord();
                                break;
                            case 1:
                                takeRecord(mPresenter.generateRecordName(), TIME_RECORD_LIMIT);
                                break;
                        }
                    }
                })
                .create();

    }

    private void remeasureET() {
        nsv.post(new Runnable() {
            @Override
            public void run() {
                etContent.setMinHeight(nsv.getHeight());
            }
        });
    }

    private void onItemClick(Media media) {
        Intent intent;
        Bundle bundle;
        switch (media.getType()) {
            case IMAGE:
                intent = new Intent(this, ImgActivity.class);
                bundle = new Bundle();
                bundle.putString("img", media.getContent());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case VIDEO:
                intent = new Intent(this, VideoActivity.class);
                bundle = new Bundle();
                bundle.putString("video", media.getContent());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case RECORD:
                break;
        }
    }

    private void onItemLongClick(final Media media) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (media.getType() == Media.Type.RECORD || media.getType() == Media.Type.VIDEO) {
                            FileUtil.INSTANCE.removeFile(media.getContent());
                        }
                        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
                        adapter.removeData(media);
                        checkRecyclerView();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void checkRecyclerView() {
        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
        if (adapter.getItemCount() == 0) {
            rvMedia.setVisibility(View.GONE);
        }else {
            rvMedia.setVisibility(View.VISIBLE);
        }
    }

    private void onBackClick() {
        onBackPressed();
    }

    private void onPublishClick() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        Toast.makeText(this, title + "\n" + content, Toast.LENGTH_SHORT).show();
    }

    private void addMedia(Media media) {
        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
        adapter.addData(Collections.singletonList(media));
        rvMedia.smoothScrollToPosition(adapter.getItemCount());
        checkRecyclerView();
    }

    private void getLocation() {
        if (new PermissionUtil(this)
                .requestPermission(Config.INSTANCE.getLOCATION_PERMISSION(), LOCATION_CODE)) {
            mPresenter.requestLocation(getApplicationContext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_note_add_photo:
                dlgPhoto.show();
                break;
            case R.id.ll_note_add_video:
                dlgVideo.show();
                break;
            case R.id.ll_note_add_record:
                dlgRecord.show();
                break;
            case R.id.ll_note_add_entry:
                Intent intent = new Intent(this, EntryChooseActivity.class);
                startActivityForResult(intent, CHOOSE_ENTRY);
                break;
            case R.id.ll_note_add_back:
                onBackClick();
                break;
            case R.id.ll_note_add_publish:
                onPublishClick();
                break;
        }
    }

    @Override
    public void onMediaBack(@NotNull String filePath) {
        Media.Type type;
        if (mCurrentMediaType == 0) {
            type = Media.Type.IMAGE;
        }else if (mCurrentMediaType == 1) {
            type = Media.Type.VIDEO;
        }else {
            type = Media.Type.RECORD;
        }
        addMedia(new Media(type, filePath));
    }

    @Override
    public void onResultWorn(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationResult(String location) {
        tvLocation.setText(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }else {
                Toast.makeText(this, "无获取地理信息权限，请进入手机设置打开对象权限",
                        Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
