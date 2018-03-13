package com.wuruoye.ichp.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/3/7.
 * this file is to
 */

public class ImgActivity extends BaseActivity implements View.OnClickListener {
    private PhotoView pv;
    private TextView tv;

    private String mImgUrl;
    private Bitmap mImgBitmap;
    @Override
    public int getContentView() {
        return R.layout.activity_img;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mImgUrl = bundle.getString("img");
    }

    @Override
    public void initView() {
        pv = findViewById(R.id.pv_img);
        tv = findViewById(R.id.tv_img_error);
        tv.setOnClickListener(this);

        pv.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                finish();
            }
        });
        pv.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
            @Override
            public void onOutsidePhotoTap(ImageView imageView) {
                finish();
            }
        });
        requestImg(mImgUrl, pv);
    }

    private void requestImg(String imgUrl, ImageView iv) {
        Glide.with(this)
                .asBitmap()
                .load(mImgUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@android.support.annotation.Nullable
                                                        GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        showError(true);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        showError(false);
                        pv.setImageBitmap(resource);
                        return false;
                    }
                })
                .submit();
    }

    private void showError(boolean isShow) {
        tv.setVisibility(isShow ? View.VISIBLE : View.GONE);
        pv.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_img_error:
                showError(false);
                requestImg(mImgUrl, pv);
                break;
        }
    }
}
