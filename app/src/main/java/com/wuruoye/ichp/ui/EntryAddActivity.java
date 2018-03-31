package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.MediaActivity;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class EntryAddActivity extends MediaActivity implements View.OnClickListener {
    public static final String[] ITEM_PHOTO = {"图库", "拍摄"};
    public static final String[] ITEM_BOTTOM = {"返回", "发布"};

    private CircleImageView civ;
    private EditText etTitle;
    private EditText etContent;
    private LinearLayout[] llBottom;

    private AlertDialog dlgPhoto;

    private String mPhotoPath;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_add;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

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
                                takePhoto("");
                                break;
                        }
                    }
                })
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
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String photo = mPhotoPath;
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
}
