package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuruoye.ichp.R;

/**
 * @Created : wuruoye
 * @Date : 2018/4/29 22:59.
 * @Description :
 */

public class HeaderMediaRVAdapter extends MediaRVAdapter {
    public static final int ADD = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_media_add_header, parent, false);
            return new ViewHolder(view);
        }else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ADD) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(null);
                }
            });
        }else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= super.getItemCount()) {
            return ADD;
        }else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
