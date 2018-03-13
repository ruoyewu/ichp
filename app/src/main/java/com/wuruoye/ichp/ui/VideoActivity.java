package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/3/8.
 * this file is to
 */

public class VideoActivity extends BaseActivity {
    private StandardGSYVideoPlayer sp;

    private String mVideoUrl;
    private boolean mIsRotation;
    @Override
    public int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mVideoUrl = bundle.getString("video");
    }

    @Override
    public void initView() {
        sp = findViewById(R.id.sp_video);

        sp.initUIState();
        sp.setUp(mVideoUrl, true, "title");
        sp.setShowFullAnimation(false);
        sp.setRotateViewAuto(true);
        sp.setRotateWithSystem(false);
        sp.setNeedShowWifiTip(false);
        sp.startWindowFullscreen(this, false, false);

        sp.getFullscreenButton().setVisibility(View.GONE);
        sp.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", sp.getPlayPosition());
        outState.putBoolean("play", sp.isInPlayingState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean play = savedInstanceState.getBoolean("play");
        int position = savedInstanceState.getInt("position");
        sp.setPlayPosition(position);
        if (play) {
            sp.startPlayLogic();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
