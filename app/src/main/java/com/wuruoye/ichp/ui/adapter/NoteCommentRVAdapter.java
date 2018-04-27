package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    public static final int TYPE_DATA = 1;
    public static final int TYPE_TAIL = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tail, parent, false);
            return new TailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_DATA) {
            NoteComment comment = getData(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvName.setText(comment.getAccount_name());
            viewHolder.tvTime.setText(DateUtil.formatTime((long)(comment.getComm_date() * 1000),
                    "yyyy/MM/dd HH:mm:ss"));
            viewHolder.tvContent.setText(comment.getContent());
            Glide.with(viewHolder.civ)
                    .load(comment.getImage_src())
                    .into(viewHolder.civ);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < super.getItemCount()) {
            return TYPE_DATA;
        }else {
            return TYPE_TAIL;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ;
        TextView tvName;
        TextView tvTime;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            civ = itemView.findViewById(R.id.civ_item_comment);
            tvName = itemView.findViewById(R.id.tv_item_comment_name);
            tvTime = itemView.findViewById(R.id.tv_item_comment_time);
            tvContent = itemView.findViewById(R.id.tv_item_comment_content);
        }
    }
}
