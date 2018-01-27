package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.model.bean.Note;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class RecommendRVAdapter extends RecyclerView.Adapter {
    private List<Note> mNoteList;
    private OnItemClickListener mListener;

    public RecommendRVAdapter(List<Note> noteList, OnItemClickListener clickListener) {
        this.mNoteList = noteList;
        this.mListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Note note = mNoteList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(note);
            }
        });
        Glide.with(viewHolder.iv)
                .load(note.getImage())
                .into(viewHolder.iv);
        viewHolder.tv.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    private void onItemClick(Note note) {
        if (mListener != null) {
            mListener.onItemClick(note);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_note_back);
            tv = itemView.findViewById(R.id.tv_note_title);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
