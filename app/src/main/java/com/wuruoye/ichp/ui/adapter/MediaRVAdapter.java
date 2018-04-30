package com.wuruoye.ichp.ui.adapter;

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
        if (holder.getItemViewType() == NORMAL || holder.getItemViewType() == AUDIO) {
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

            }else {
                ViewHolder viewHolder = (ViewHolder) holder;
                if (media.getType() == Media.Type.IMAGE) {
                    Glide.with(viewHolder.ivBack)
                            .load(media.getContent())
                            .into(viewHolder.ivBack);
                }
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
        private View vBg1;
        private View vBg2;
        ImageView iv;

        public AudioViewHolder(View itemView) {
            super(itemView);
            vBg1 = itemView.findViewById(R.id.v_item_audio_1);
            vBg2 = itemView.findViewById(R.id.v_item_audio_2);
            iv = itemView.findViewById(R.id.iv_item_audio);
        }
    }
}
