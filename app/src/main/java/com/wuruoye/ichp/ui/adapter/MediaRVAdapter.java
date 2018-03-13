package com.wuruoye.ichp.ui.adapter;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Media;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class MediaRVAdapter extends BaseRVAdapter<Media> {
    private static final int NORMAL = 1;
    public static final int AUDIO = 2;

    private OnActionListener mOnActionListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_media, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_media_audio, parent, false);
            return new AudioViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == NORMAL) {
            final Media media = getData(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(media);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick(media);
                    return true;
                }
            });
            if (media.getType() == Media.Type.RECORD) {
                final AudioViewHolder viewHolder = (AudioViewHolder) holder;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolder.isPlaying()) {
                            viewHolder.stop();
                        }else {
                            viewHolder.start();
                        }
                    }
                });
            }else {
                ViewHolder viewHolder = (ViewHolder) holder;
                Glide.with(viewHolder.ivBack)
                        .load(media.getContent())
                        .into(viewHolder.ivBack);
                if (media.getType() == Media.Type.VIDEO) {
                    viewHolder.ivTag.setVisibility(View.VISIBLE);
                    viewHolder.ivTag.setImageResource(R.drawable.ic_play);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getData().get(position).getType() == Media.Type.RECORD) {
            return AUDIO;
        }else {
            return NORMAL;
        }
    }

    public void setOnAddItemClick(OnActionListener listener) {
        this.mOnActionListener = listener;
    }

    private void onAudioStart(Media media) {
        if (mOnActionListener != null) {
            mOnActionListener.onAudioStart(media);
        }
    }

    private void onAudioStop(Media media) {
        if (mOnActionListener != null) {
            mOnActionListener.onAudioStop(media);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBack;
        ImageView ivTag;

        ViewHolder(View itemView) {
            super(itemView);
            ivBack = itemView.findViewById(R.id.iv_media_back);
            ivTag = itemView.findViewById(R.id.iv_media_tag);
        }
    }

    class AudioViewHolder extends RecyclerView.ViewHolder {
        public static final int TIME_DURATION = 1000;

        private View vBg1;
        private View vBg2;
        ImageView iv;

        private ValueAnimator animator1;
        private ValueAnimator animator2;
        public AudioViewHolder(View itemView) {
            super(itemView);
            vBg1 = itemView.findViewById(R.id.v_item_audio_1);
            vBg2 = itemView.findViewById(R.id.v_item_audio_2);
            iv = itemView.findViewById(R.id.iv_item_audio);

            initAnimator();
        }

        private void initAnimator() {
            animator1 = new ValueAnimator();
            animator2 = new ValueAnimator();
            animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    vBg1.setScaleX(scale);
                    vBg1.setScaleY(scale);
                }
            });
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    vBg2.setScaleX(scale);
                    vBg2.setScaleY(scale);
                }
            });
            animator1.setRepeatCount(-1);
            animator1.setRepeatMode(ValueAnimator.REVERSE);
            animator2.setRepeatCount(-1);
            animator2.setRepeatMode(ValueAnimator.REVERSE);
        }

        public void start() {
            animator1.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animator2.start();
                }
            }, TIME_DURATION / 2);
        }

        public void pause() {

        }

        public void stop() {
            animator1.cancel();
            animator2.cancel();
            vBg1.setScaleX(1F);
            vBg1.setScaleY(1F);
            vBg2.setScaleX(1F);
            vBg2.setScaleY(1F);
        }

        public boolean isPlaying() {
            return animator1.isRunning() || animator2.isRunning();
        }
    }

    public interface OnActionListener {
        void onAddClick();
        void onAudioStart(Media media);
        void onAudioStop(Media media);
    }
}
