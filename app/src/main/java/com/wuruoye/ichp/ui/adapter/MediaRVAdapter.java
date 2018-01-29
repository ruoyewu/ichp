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
    private static final int ADD = 2;

    private OnAddItemClickListener mOnAddItemClick;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (getItemViewType(position) == NORMAL) {
            final Media media = getData(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(media);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick(media);
                    return true;
                }
            });
            if (media.getType() == Media.Type.RECORD) {
                viewHolder.ivTag.setVisibility(View.GONE);
                viewHolder.ivBack.setImageResource(R.drawable.ic_mic);
            }else {
                Glide.with(viewHolder.ivBack)
                        .load(media.getContent())
                        .into(viewHolder.ivBack);
                if (media.getType() == Media.Type.VIDEO) {
                    viewHolder.ivTag.setVisibility(View.VISIBLE);
                    viewHolder.ivTag.setImageResource(R.drawable.ic_play);
                }
            }
        }else {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddItemClick();
                }
            });
            viewHolder.ivBack.setImageResource(R.drawable.ic_add);
            viewHolder.ivTag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < super.getItemCount()) {
            return NORMAL;
        }else {
            return ADD;
        }
    }

    public void setOnAddItemClick(OnAddItemClickListener listener) {
        this.mOnAddItemClick = listener;
    }

    private void onAddItemClick() {
        if (mOnAddItemClick != null) {
            mOnAddItemClick.onAddClick();
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

    public interface OnAddItemClickListener {
        void onAddClick();
    }
}
