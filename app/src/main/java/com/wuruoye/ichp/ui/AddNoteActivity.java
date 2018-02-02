package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.wuruoye.ichp.ui.adapter.EntryAddRVAdapter;
import com.wuruoye.ichp.ui.adapter.MediaRVAdapter;
import com.wuruoye.ichp.ui.contract.AddNoteContract;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.presenter.DevAddNotePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class AddNoteActivity extends MediaActivity
        implements View.OnClickListener, AddNoteContract.View {
    public static final String[] MEDIA_ITEMS =
            {"照片选取", "照片拍摄", "视频选取", "视频拍摄", "音频选取", "音频录制"};
    public static final int LOCATION_CODE = 1;
    public static final int TIME_RECORD_LIMIT = 100000;

    private EditText etTitle;
    private EditText etContent;
    private TextView tvLocation;
    private RecyclerView rvMedia;
    private RecyclerView rvEntry;
    private ImageButton ibAddEntry;
    private LinearLayout llBack;
    private LinearLayout llPublish;

    private AlertDialog adMedia;

    private AddNoteContract.Presenter mPresenter;
    private int mCurrentMediaType;

    @Override
    public int getContentView() {
        return R.layout.activity_add_note;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mPresenter = new DevAddNotePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView() {
        etTitle = findViewById(R.id.et_add_note_title);
        etContent = findViewById(R.id.et_add_note_content);
        tvLocation = findViewById(R.id.tv_add_note_location);
        rvMedia = findViewById(R.id.rv_add_note_media);
        rvEntry = findViewById(R.id.rv_add_note_entry);
        ibAddEntry = findViewById(R.id.ib_add_note_entry);
        llBack = findViewById(R.id.ll_add_note_back);
        llPublish = findViewById(R.id.ll_add_note_publish);

        initLayout();
        initRecyclerView();
        initDialog();
        getLocation();
    }

    private void initLayout() {
        ImageView ivBack = llBack.findViewById(R.id.iv_icon_text);
        TextView tvBack = llBack.findViewById(R.id.tv_icon_text);
        ivBack.setImageResource(R.drawable.ic_goleft);
        tvBack.setText("返回");

        ImageView ivPublish = llPublish.findViewById(R.id.iv_icon_text);
        TextView tvPublish = llPublish.findViewById(R.id.tv_icon_text);
        ivPublish.setImageResource(R.drawable.ic_edit);
        tvPublish.setText("发布");

        llBack.setOnClickListener(this);
        llPublish.setOnClickListener(this);
        ibAddEntry.setOnClickListener(this);
    }

    private void initRecyclerView() {
        MediaRVAdapter adapter = new MediaRVAdapter();
        adapter.setOnAddItemClick(new MediaRVAdapter.OnAddItemClickListener() {
            @Override
            public void onAddClick() {
                onAddMedia();
            }
        });
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<Media>() {
            @Override
            public void onItemClick(Media model) {
                AddNoteActivity.this.onItemClick(model);
            }
        });
        adapter.setOnItemLongClickListener(new BaseRVAdapter.OnItemLongClickListener<Media>() {
            @Override
            public void onItemLongClick(Media model) {
                AddNoteActivity.this.onItemLongClick(model);
            }
        });
        rvMedia.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvMedia.setAdapter(adapter);

        EntryAddRVAdapter entryAddRVAdapter = new EntryAddRVAdapter();
        rvEntry.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvEntry.setAdapter(entryAddRVAdapter);
    }

    private void initDialog() {
        adMedia = new AlertDialog.Builder(this)
                .setItems(MEDIA_ITEMS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getMedia(which);
                    }
                })
                .create();
    }

    private void onAddMedia() {
        adMedia.show();
    }

    private void onItemClick(Media media) {
        Toast.makeText(this, media.getContent(), Toast.LENGTH_SHORT).show();
    }

    private void onItemLongClick(Media media) {
        if (media.getType() == Media.Type.RECORD || media.getType() == Media.Type.VIDEO) {
            FileUtil.INSTANCE.removeFile(media.getContent());
        }
        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
        adapter.removeData(media);
    }

    private void onBackClick() {
        onBackPressed();
    }

    private void onPublishClick() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        Toast.makeText(this, title + "\n" + content, Toast.LENGTH_SHORT).show();
    }

    private void onAddEntryClick() {

    }

    private void addMedia(Media media) {
        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
        adapter.addData(Collections.singletonList(media));
        rvMedia.smoothScrollToPosition(adapter.getItemCount());
    }

    private void setEntry(List<Entry> entryList) {
        EntryAddRVAdapter adapter = (EntryAddRVAdapter) rvEntry.getAdapter();
        adapter.setData(entryList);
    }

    private void getLocation() {
        if (new PermissionUtil(this)
                .requestPermission(Config.INSTANCE.getLOCATION_PERMISSION(), LOCATION_CODE)) {
            mPresenter.requestLocation(getApplicationContext());
        }
    }

    private void getMedia(int type) {
        mCurrentMediaType = type;
        switch (type) {
            case 0:
                choosePhoto();
                break;
            case 1:
                takePhoto(mPresenter.generateImageName());
                break;
            case 2:
                chooseVideo();
                break;
            case 3:
                takeVideo();
                break;
            case 4:
                chooseRecord();
                break;
            case 5:
                takeRecord(mPresenter.generateVoiceName(), TIME_RECORD_LIMIT);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_note_back:
                onBackClick();
                break;
            case R.id.ll_add_note_publish:
                onPublishClick();
                break;
            case R.id.ib_add_note_entry:
                onAddEntryClick();
                break;
        }
    }

    @Override
    public void onMediaBack(@NotNull String filePath) {
        Media.Type type;
        if (mCurrentMediaType == 0 || mCurrentMediaType == 1) {
            type = Media.Type.IMAGE;
        }else if (mCurrentMediaType == 2 || mCurrentMediaType == 3) {
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
