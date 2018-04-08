package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.Note;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class RecordRVAdapter extends BaseRVAdapter<Note> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Note note = getData(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(note);
            }
        });
        viewHolder.tv.setText(note.getTitle());
        String[] url = note.getUrl().split(",");
        if (url.length > 0) {
            Glide.with(viewHolder.iv)
                    .load(url[0])
                    .into(viewHolder.iv);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_note_rec_back);
            tv = itemView.findViewById(R.id.tv_note_rec_title);
        }
    }
}
