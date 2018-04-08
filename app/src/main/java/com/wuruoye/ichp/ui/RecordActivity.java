package com.wuruoye.ichp.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.library.ui.WBaseActivity;

import java.io.IOException;

/**
 * @Created : wuruoye
 * @Date : 2018/4/7.
 * @Description : 音频播放界面
 */

public class RecordActivity extends WBaseActivity implements View.OnClickListener {
    private TextView tvTip;

    private String mRecord;

    private enum State {
        PREPARE,
        PLAYING,
        PAUSING,
        ENDING,
        ERROR
    }
    public static final String PREPARE = "正在加载音频中...";
    public static final String PLAYING = "正在播放音频，点击暂停";
    public static final String PAUSING = "暂定播放音频，点击继续";
    public static final String ENDING = "播放音频结束，点击重放";
    public static final String ERROR = "播放音频失败，点击重试";

    private State mState;

    private MediaPlayer mPlayer = new MediaPlayer();
    @Override
    protected int getContentView() {
        return R.layout.activity_record;
    }

    @Override
    protected void initData(Bundle bundle) {
        mRecord = bundle.getString("record");
    }

    @Override
    protected void initView() {
        tvTip = findViewById(R.id.tv_record_tip);
        tvTip.setOnClickListener(this);

        prepare();
    }

    @Override
    public void onClick(View view) {
        switch (mState) {
            case PREPARE:
                break;
            case ENDING:
                play();
                break;
            case ERROR:
                prepare();
                break;
            case PLAYING:
                pause();
                break;
            case PAUSING:
                play();
        }
    }

    private void prepare() {
        try {
            changeState(State.PREPARE);
            if (mRecord.startsWith("http")) {
                Uri uri = Uri.parse(mRecord);
                mPlayer.setDataSource(this, uri);
            }else {
                mPlayer.setDataSource(mRecord);
            }

            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    play();
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    changeState(State.ENDING);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            mPlayer.reset();
            changeState(State.ERROR);
        }
    }

    private void play() {
        changeState(State.PLAYING);
        mPlayer.start();
    }

    private void pause() {
        changeState(State.PAUSING);
        mPlayer.pause();
    }

    private void changeState(State state) {
        mState = state;
        switch (state) {
            case PREPARE:
                tvTip.setText(PREPARE);
                break;
            case PLAYING:
                tvTip.setText(PLAYING);
                break;
            case PAUSING:
                tvTip.setText(PAUSING);
                break;
            case ERROR:
                tvTip.setText(ERROR);
                break;
            case ENDING:
                tvTip.setText(ENDING);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPlayer.stop();
        mPlayer.release();
        super.onDestroy();
    }
}
