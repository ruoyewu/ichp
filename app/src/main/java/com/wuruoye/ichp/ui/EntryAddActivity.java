package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.EntryAddContract;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.presenter.pro.EntryAddPresenter;
import com.wuruoye.library.ui.WPhotoActivity;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class EntryAddActivity extends WPhotoActivity<EntryAddContract.Presenter>
        implements View.OnClickListener, EntryAddContract.View {
    public static final String[] ITEM_PHOTO = {"图库", "拍摄"};
    public static final String[] ITEM_BOTTOM = {"返回", "发布"};

    private CircleImageView civ;
    private EditText etTitle;
    private EditText etContent;
    private LinearLayout[] llBottom;

    private AlertDialog dlgPhoto;
    private AlertDialog dlgUpload;

    private String mPhotoPath;
    private String mPhotoUrl;

    private int mType = EntryAddContract.TYPE_ADD;
    private Entry mEntry;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_add;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        try {
            mType = bundle.getInt("type");
            mEntry = bundle.getParcelable("entry");
            mPhotoUrl = mEntry.getUrl();
        } catch (Exception ignored) {

        }

        setPresenter(new EntryAddPresenter());
    }

    @Override
    public void initView() {
        llBottom = new LinearLayout[2];
        civ = findViewById(R.id.civ_add_entry);
        etTitle = findViewById(R.id.et_add_entry_title);
        etContent = findViewById(R.id.et_add_entry_content);
        llBottom[0] = findViewById(R.id.ll_add_entry_back);
        llBottom[1] = findViewById(R.id.ll_add_entry_publish);

        initLayout();
        initDialog();
    }

    private void initLayout() {
        for (int i = 0; i < ITEM_BOTTOM.length; i++) {
            ImageView iv = llBottom[i].findViewById(R.id.iv_icon_text);
            TextView tv = llBottom[i].findViewById(R.id.tv_icon_text);
            tv.setText(ITEM_BOTTOM[i]);
            llBottom[i].setOnClickListener(this);
        }

        civ.setOnClickListener(this);

        if (mType == EntryAddContract.TYPE_MODIFY) {
            etTitle.setEnabled(false);
            etTitle.setText(mEntry.getName());
            etContent.setText(mEntry.getContent());
            Glide.with(civ)
                    .load(mEntry.getUrl())
                    .into(civ);
        }
    }

    private void initDialog() {
        dlgPhoto = new AlertDialog.Builder(this)
                .setTitle("获取照片方式")
                .setItems(ITEM_PHOTO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhoto();
                                break;
                            case 1:
                                takePhoto(mPresenter.generatePhotoPath());
                                break;
                        }
                    }
                })
                .create();
        dlgUpload = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_add_entry:
                dlgPhoto.show();
                break;
            case R.id.ll_add_entry_back:
                onBackPressed();
                break;
            case R.id.ll_add_entry_publish:
                publishEntry();
                break;
        }
    }

    private void publishEntry() {
        if (TextUtils.isEmpty(mPhotoPath)) {
            if (mType == EntryAddContract.TYPE_ADD) {
                Toast.makeText(this, "请添加图片", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        dlgUpload.setMessage("正在上传媒体文件...");
        dlgUpload.show();
        if (TextUtils.isEmpty(mPhotoPath)) {
            doPublish();
        }else {
            mPresenter.requestUpload(mPhotoPath);
        }
    }

    private void doPublish() {
        dlgPhoto.setMessage("正在上传信息...");
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (mType == EntryAddContract.TYPE_ADD) {
            mPresenter.requestAddEntry(title, content, mPhotoUrl);
        }else {
            mPresenter.requestModifyEntry(mEntry.getEntry_id(), content, mPhotoUrl);
        }
    }

    @Override
    public void onPhotoBack(String s) {
        mPhotoPath = s;
        Glide.with(this)
                .load(s)
                .into(civ);
    }

    @Override
    public void onPhotoError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultAdd(boolean result, String info) {
        dlgUpload.dismiss();
        if (result) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultUpload(boolean result, String info) {
        if (result) {
            mPhotoUrl = info;
            doPublish();
        }else {
            dlgUpload.dismiss();
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultModify() {
        Toast.makeText(this, "修改完成", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
