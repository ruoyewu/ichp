package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.model.bean.Message;
import com.wuruoye.ichp.ui.model.bean.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class MessagePersonRVAdapter extends BaseRVAdapter<Message> {
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;

    private User mUser;

    public MessagePersonRVAdapter(User user) {
        mUser = user;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEFT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_left, parent, false);
            return new LeftViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_right, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = getData(position);
        if (getItemViewType(position) == TYPE_LEFT) {
            LeftViewHolder left = (LeftViewHolder) holder;

        }else if (getItemViewType(position) == TYPE_RIGHT) {
            RightViewHolder right = (RightViewHolder) holder;

            right.tvName.setText(message.getFrom().getName());
            right.tvContent.setText(message.getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getData(position);
        if (message.getFrom() == mUser) {
            return TYPE_RIGHT;
        }else {
            return TYPE_LEFT;
        }
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ;
        TextView tvName;
        TextView tvContent;

        public LeftViewHolder(View itemView) {
            super(itemView);

            civ = itemView.findViewById(R.id.civ_item_message_left);
            tvName = itemView.findViewById(R.id.tv_item_message_left_name);
            tvContent = itemView.findViewById(R.id.tv_item_message_left_content);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ;
        TextView tvName;
        TextView tvContent;

        public RightViewHolder(View itemView) {
            super(itemView);
            civ = itemView.findViewById(R.id.civ_item_message_right);
            tvName = itemView.findViewById(R.id.tv_item_message_right_name);
            tvContent = itemView.findViewById(R.id.tv_item_message_right_content);
        }
    }
}
