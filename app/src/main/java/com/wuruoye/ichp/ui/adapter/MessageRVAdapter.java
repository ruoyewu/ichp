package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.base.util.DateUtil;
import com.wuruoye.ichp.ui.model.bean.Comment;
import com.wuruoye.ichp.ui.model.bean.Message;
import com.wuruoye.ichp.ui.model.bean.Praise;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class MessageRVAdapter extends BaseRVAdapter<Object> {
    private OnItemSeeClickListener onItemSeeClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Object data = getData(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSeeClick(data);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(data);
            }
        });
        if (data instanceof Praise) {
            viewHolder.tvTitle.setText(((Praise) data).getFrom());
            viewHolder.tvTime.setText(DateUtil.INSTANCE.formatTime(((Praise) data).getTime(),
                    "yyyy-MM-dd"));
        }else if (data instanceof Comment) {
            viewHolder.tvTitle.setText(((Comment) data).getFrom());
            viewHolder.tvTime.setText(DateUtil.INSTANCE.formatTime(((Comment) data).getTime(),
                    "yyyy-MM-dd"));
        }else if (data instanceof Message) {
            viewHolder.tvTitle.setText(((Message) data).getFrom().getName());
            viewHolder.tvTime.setText(DateUtil.INSTANCE.formatTime(((Message) data).getTime(),
                    "yyyy-MM-dd"));
        }
    }

    private void onItemSeeClick(Object object) {
        if (onItemSeeClickListener != null) {
            onItemSeeClickListener.onItemSeeClick(object);
        }
    }

    public void setOnItemSeeClickListener(OnItemSeeClickListener listener) {
        this.onItemSeeClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        ImageButton ib;
        TextView tvSee;
        LinearLayout ll;

        ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_message_title);
            tvTime = itemView.findViewById(R.id.tv_message_time);
            ib = itemView.findViewById(R.id.ib_message_see);
            tvSee = itemView.findViewById(R.id.tv_message_see);
            ll = itemView.findViewById(R.id.ll_message_see);
        }
    }

    public interface OnItemSeeClickListener {
        void onItemSeeClick(Object object);
    }
}
