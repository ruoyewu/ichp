package com.wuruoye.ichp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.library.util.log.WLog;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/3/8.
 * this file is to
 */

public class VideoActivity extends BaseActivity {
    private StandardGSYVideoPlayer sp;

    private String mVideoUrl;

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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        WLog.loge(this, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
