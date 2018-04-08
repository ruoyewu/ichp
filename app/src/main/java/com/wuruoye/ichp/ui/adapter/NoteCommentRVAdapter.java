package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
import com.wuruoye.library.util.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/24.
 * this file is to
 */

public class NoteCommentRVAdapter extends BaseRVAdapter<NoteComment> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteComment comment = getData(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setText(String.valueOf(comment.getCommer()));
        viewHolder.tvTime.setText(DateUtil.formatTime((long)(comment.getComm_date() * 1000),
                "yyyy / MM / dd HH : mm : ss"));
        viewHolder.tvContent.setText(comment.getContent());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ;
        TextView tvName;
        TextView tvTime;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
