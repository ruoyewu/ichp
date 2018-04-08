package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.library.ui.WBaseFragment;

/**
 * @Created : wuruoye
 * @Date : 2018/4/7.
 * @Description : 展示单个媒体文件界面
 */

public class MediaFragment extends WBaseFragment implements View.OnClickListener {
    private ImageView ivBg;
    private ImageView ivPlay;

    private Media mMedia;

    @Override
    protected int getContentView() {
        return R.layout.fragment_media;
    }

    @Override
    protected void initData(Bundle bundle) {
        mMedia = bundle.getParcelable("media");
    }

    @Override
    protected void initView(View view) {
        ivBg = view.findViewById(R.id.iv_media);
        ivPlay = view.findViewById(R.id.iv_media_play);

        ivBg.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        switch (mMedia.getType()) {
            case RECORD:
                ivBg.setVisibility(View.GONE);
                ivPlay.setVisibility(View.VISIBLE);
                break;
            case VIDEO:
                Glide.with(ivBg)
                        .load(mMedia.getContent())
                        .into(ivBg);
                ivPlay.setVisibility(View.VISIBLE);
                break;
            case IMAGE:
                Glide.with(ivBg)
                        .load(mMedia.getContent())
                        .into(ivBg);
                ivPlay.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_media:
                switch (mMedia.getType()) {
                    case IMAGE:
                        intent = new Intent(getContext(), ImgActivity.class);
                        bundle.putString("img", mMedia.getContent());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.iv_media_play:
                switch (mMedia.getType()) {
                    case VIDEO:
                        intent = new Intent(getContext(), VideoActivity.class);
                        bundle.putString("video", mMedia.getContent());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case RECORD:
                        break;
                }
                break;
        }
    }
}
