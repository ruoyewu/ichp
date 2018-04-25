package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.adapter.MediaRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.AddNoteContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.NoteAddPresenter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class NoteAddActivity extends MediaActivity<AddNoteContract.Presenter>
        implements View.OnClickListener, AddNoteContract.View {
    public static final String[] PHOTO_ITEM = {"照片选取", "照片拍摄"};
    public static final String[] VIDEO_ITEM = {"视频选取", "视频拍摄"};
    public static final String[] RECORD_ITEM = {"音频选取", "音频拍摄"};
    public static final String[] TITLE_IT_ITEM = {"照片", "视频", "音频", "词条", "返回", "发布"};
    public static final int[] ICON_IT_ITEM = {R.drawable.ic_photo, R.drawable.ic_movies,
            R.drawable.ic_audio, R.drawable.ic_entry_black, R.drawable.ic_goleft, R.drawable.ic_edit};
    public final int LOCATION_CODE = hashCode() % 10000;
    public static final int TIME_RECORD_LIMIT = 100000;
    public static final int CHOOSE_ENTRY = 201;

    public static final int TYPE_NOTE = 1;
    public static final int TYPE_COURSE = 2;

    private EditText etTitle;
    private NestedScrollView nsv;
    private EditText etContent;
    private TextView tvLocation;
    private LinearLayout llLocation;
    private LinearLayout llCourseInfo;
    private EditText etDate;
    private EditText etPlace;
    private EditText etEntrance;
    private RecyclerView rvEntry;
    private RecyclerView rvMedia;
    private LinearLayout[] llList;

    private AlertDialog dlgPhoto;
    private AlertDialog dlgVideo;
    private AlertDialog dlgRecord;
    private AlertDialog dlgUpload;
    private AlertDialog dlgUploadError;

    private int mCurrentMediaType;
    private int mType;

    private Double[] mAddress;
    private String[] mLocation;
    private HashMap<String, Integer> mUrlMap = new HashMap<>();
    private List<Media> mMediaList = new ArrayList<>();
    private Media mCurrentUploadMedia;

    @Override
    public int getContentView() {
        return R.layout.activity_note_add;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");

        setPresenter(new NoteAddPresenter());
    }

    @Override
    public void initView() {
        llList = new LinearLayout[6];
        etTitle = findViewById(R.id.et_note_add_title);
        nsv = findViewById(R.id.nsv_note_add);
        etContent = findViewById(R.id.et_note_add_content);
        tvLocation = findViewById(R.id.tv_note_add_location);
        llLocation = findViewById(R.id.ll_note_add_location);
        llCourseInfo = findViewById(R.id.ll_note_add_course_info);
        etDate = findViewById(R.id.et_note_add_date);
        rvEntry = findViewById(R.id.rv_note_add_entry);
        rvMedia = findViewById(R.id.rv_note_add_media);
        etPlace = findViewById(R.id.et_note_add_place);
        etEntrance = findViewById(R.id.et_note_add_entrance);
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
        if (requestCode == CHOOSE_ENTRY && resultCode == RESULT_OK) {
            List<Entry> entryList = data.getParcelableArrayListExtra("entry");
            EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvEntry.getAdapter();
            adapter.setData(entryList);
        }
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
            iv.setImageResource(ICON_IT_ITEM[i]);
            tv.setText(TITLE_IT_ITEM[i]);
            tv.setTextColor(ActivityCompat.getColor(this, R.color.black));
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

        EntryChooseRVAdapter entryChooseRVAdapter = new EntryChooseRVAdapter();
        rvEntry.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvEntry.setAdapter(entryChooseRVAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this, R.drawable.decoration_horizontal));
        rvEntry.addItemDecoration(decoration);
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
        dlgUpload = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("提示")
                .create();
        dlgUploadError = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("上传文件出错，是否重新上传？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reUploadFile();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mMediaList.clear();
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
                intent = new Intent(this, RecordActivity.class);
                bundle = new Bundle();
                bundle.putString("record", media.getContent());
                intent.putExtras(bundle);
                startActivity(intent);
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
//        if (mUrlMap.size() == 0) {
//            Toast.makeText(this, "至少添加一张图片", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (mMediaList.size() > 0) {
            dlgUpload.setMessage("正在上传媒体文件...");
            dlgUpload.show();
            mCurrentUploadMedia = mMediaList.get(0);
            mPresenter.requestUploadFile(mCurrentUploadMedia.getContent(),
                    getType(mCurrentUploadMedia));
        }else {
            if (mType == TYPE_NOTE) {
                doPublishNote();
            }else {
                doPublishCourse();
            }
        }
    }

    private void reUploadFile() {
        dlgUpload.setMessage("正在重传媒体文件...");
        dlgUpload.show();
        if (mMediaList.size() > 0) {
            mCurrentUploadMedia = mMediaList.get(0);
            mPresenter.requestUploadFile(mCurrentUploadMedia.getContent(),
                    getType(mCurrentUploadMedia));
        }else {
            doPublishNote();
        }
    }

    private void doPublishCourse() {
        String title = etTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            onResultError("title is empty");
            return;
        }
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            onResultError("content is empty");
            return;
        }
        String date = etDate.getText().toString();
        if (TextUtils.isEmpty(date)) {
            onResultError("date is empty");
            return;
        }
        String place = etPlace.getText().toString();
        if (TextUtils.isEmpty(place)) {
            onResultError("place is empty");
            return;
        }
        String entrance = etEntrance.getText().toString();
        if (TextUtils.isEmpty(entrance)) {
            onResultError("entrance is empty");
            return;
        }

        StringBuilder entryBuilder = new StringBuilder();
        List<Entry> entryList = ((EntryChooseRVAdapter) rvEntry.getAdapter()).getData();
        if (entryList.size() > 0) {
            for (int i = 0; i < entryList.size() - 1; i++) {
                entryBuilder.append(entryList.get(i).getEntry_id()).append(",");
            }
            entryBuilder.append(entryList.get(entryList.size() - 1).getEntry_id());
        }else {
            onResultError("entry is empty");
            return;
        }

        StringBuilder urlBuilder = new StringBuilder();
        StringBuilder typeBuilder = new StringBuilder();
        int size = mUrlMap.size();
        int j = 0;
        for (Map.Entry<String, Integer> entry : mUrlMap.entrySet()) {
            if (j++ < size - 1) {
                urlBuilder.append(entry.getKey()).append(",");
                typeBuilder.append(entry.getValue()).append(",");
            }else {
                urlBuilder.append(entry.getKey());
                typeBuilder.append(entry.getValue());
            }
        }

        Course course = new Course();
        course.setTitle(title);
        course.setContent(content);
        course.setHold_addr(place);
        course.setAct_src(entrance);
        course.setLabels_id_str(entryBuilder.toString());
        course.setImage_src(urlBuilder.toString());
        course.setType(typeBuilder.toString());

        mPresenter.requestUpCourse(course, date);
    }

    private void doPublishNote() {
        String title = etTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "title is empty", Toast.LENGTH_SHORT).show();
            dlgUpload.dismiss();
            return;
        }
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "content is empty", Toast.LENGTH_SHORT).show();
            dlgUpload.dismiss();
            return;
        }
        StringBuilder urlBuilder = new StringBuilder();
        StringBuilder typeBuilder = new StringBuilder();
        int size = mUrlMap.size();
        int j = 0;
        for (Map.Entry<String, Integer> entry : mUrlMap.entrySet()) {
            if (j++ < size - 1) {
                urlBuilder.append(entry.getKey()).append(",");
                typeBuilder.append(entry.getValue()).append(",");
            }else {
                urlBuilder.append(entry.getKey());
                typeBuilder.append(entry.getValue());
            }
        }
        String addr = "";
        if (mLocation != null && mLocation.length > 0) {
            addr += mLocation[0] + ',';
        }else {
            addr += " ,";
        }
        if (mAddress != null) {
            addr += mAddress[0] + "," + mAddress[1];
        }else {
            addr += "0,0";
        }
        StringBuilder entryBuilder = new StringBuilder();
        List<Entry> entryList = ((EntryChooseRVAdapter) rvEntry.getAdapter()).getData();
        if (entryList.size() > 0) {
            for (int i = 0; i < entryList.size() - 1; i++) {
                entryBuilder.append(entryList.get(i).getEntry_id()).append(",");
            }
            entryBuilder.append(entryList.get(entryList.size() - 1).getEntry_id());
        }else {
            Toast.makeText(this, "entry is empty", Toast.LENGTH_SHORT).show();
            dlgUpload.dismiss();
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setDiscribe(content);
        note.setUrl(urlBuilder.toString());
        note.setType(typeBuilder.toString());
        note.setAddr(addr);
        note.setLabels_id_str(entryBuilder.toString());
        mPresenter.requestUpNote(note);
        dlgUpload.setMessage("正在上传记录...");
    }

    private String getType(Media media) {
        switch (media.getType()) {
            case RECORD:
                return "audio/*";
            case VIDEO:
                return "video/*";
            case IMAGE:
                return "image/*";
                default:
                    return "file/*";
        }
    }

    private int getIntType(Media media) {
        switch (media.getType()) {
            case IMAGE:
                return 1;
            case VIDEO:
                return 2;
            case RECORD:
                return 3;
            default:
                return 0;
        }
    }

    private void addMedia(Media media) {
        mMediaList.add(media);
        MediaRVAdapter adapter = (MediaRVAdapter) rvMedia.getAdapter();
        adapter.addData(Collections.singletonList(media));
        rvMedia.smoothScrollToPosition(adapter.getItemCount());
        checkRecyclerView();
    }

    private void getLocation() {
        if (mType == TYPE_NOTE) {
            if (new PermissionUtil(this)
                    .requestPermission(Config.INSTANCE.getLOCATION_PERMISSION(), LOCATION_CODE)) {
                mPresenter.requestLocation(getApplicationContext());
            }
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
                Bundle bundle = new Bundle();
                List<Entry> entryList = ((EntryChooseRVAdapter)rvEntry.getAdapter()).getData();
                bundle.putParcelableArrayList("entry", (ArrayList<? extends Parcelable>) entryList);
                intent.putExtras(bundle);
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
    public void onResultError(String error) {
        dlgUpload.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationResult(Double[] addr, String[] location) {
        mAddress = addr;
        mLocation = location;
        if (mLocation.length > 0) {
            tvLocation.setText(mLocation[0]);
        }else {
            tvLocation.setText("暂无位置信息");
        }
    }

    @Override
    public void onLocationError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFileUploadResult(boolean result, String url) {
        if (result) {
            mMediaList.remove(mCurrentUploadMedia);
            int type = getIntType(mCurrentUploadMedia);
            mUrlMap.put(url, type);
            if (mMediaList.size() > 0) {
                mCurrentUploadMedia = mMediaList.get(0);
                mPresenter.requestUploadFile(mCurrentUploadMedia.getContent(),
                        getType(mCurrentUploadMedia));
            }else {
                if (mType == TYPE_NOTE) {
                    doPublishNote();
                }else {
                    doPublishCourse();
                }
            }
        }else {
            dlgUpload.dismiss();
            dlgUploadError.show();
        }
    }

    @Override
    public void onNoteAddResult(boolean result, String info) {
        dlgUpload.dismiss();
        if (result) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultCourse() {
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        finish();
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

    @Override
    public void onPhotoBack(String s) {
        Media.Type type;
        if (mCurrentMediaType == 0) {
            type = Media.Type.IMAGE;
        }else if (mCurrentMediaType == 1) {
            type = Media.Type.VIDEO;
        }else {
            type = Media.Type.RECORD;
        }
        addMedia(new Media(type, s));
    }

    @Override
    public void onPhotoError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
