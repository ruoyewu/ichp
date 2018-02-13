package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Entry;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/12.
 * this file is to
 */

public class EntrySearchRVAdapter extends BaseRVAdapter<Entry> {
    private OnAddClickListener onAddClickListener;

    private List<Entry> mChoseEntryList;

    public EntrySearchRVAdapter(List<Entry> entryList) {
        mChoseEntryList = entryList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Entry entry = getData(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvTitle.setText(entry.getTitle());
        viewHolder.tvContent.setText(entry.getTitle());
        if (mChoseEntryList.contains(entry)) {
            viewHolder.tv.setVisibility(View.VISIBLE);
            viewHolder.iv.setVisibility(View.GONE);
        }else {
            viewHolder.tv.setVisibility(View.GONE);
            viewHolder.iv.setVisibility(View.VISIBLE);
            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddClickListener != null) {
                        viewHolder.iv.setVisibility(View.GONE);
                        viewHolder.tv.setVisibility(View.VISIBLE);
                        onAddClickListener.onAddClick(entry);
                    }
                }
            });
        }
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        onAddClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tv;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_entry_search_title);
            tvContent = itemView.findViewById(R.id.tv_item_entry_search_content);
            tv = itemView.findViewById(R.id.tv_item_entry_search);
            iv = itemView.findViewById(R.id.iv_item_entry_search);
        }
    }

    public interface OnAddClickListener {
        void onAddClick(Entry entry);
    }
}
