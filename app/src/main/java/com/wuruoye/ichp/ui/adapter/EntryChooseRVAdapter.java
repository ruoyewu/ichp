package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Entry;

/**
 * Created by wuruoye on 2018/2/12.
 * this file is to
 */

public class EntryChooseRVAdapter extends BaseRVAdapter<Entry> {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_HEAD = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry_choose, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (getItemViewType(position) == TYPE_NORMAL) {
            final Entry entry = getData(position - 1);
            viewHolder.tv.setText(entry.getName());
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick(entry);
                    return true;
                }
            });
        }else {
            viewHolder.tv.setText("已选词条");
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }else {
            return TYPE_NORMAL;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item_entry_choose);
        }
    }
}
