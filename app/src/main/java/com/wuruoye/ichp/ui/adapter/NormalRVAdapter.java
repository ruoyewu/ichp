package com.wuruoye.ichp.ui.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class NormalRVAdapter extends BaseRVAdapter<Object> {

    private boolean mShowCheck = false;
    private OnActionListener mOnActionListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_normal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Object data = getData(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(data);
            }
        });
        if (mShowCheck) {
            viewHolder.acb.setVisibility(View.VISIBLE);
            viewHolder.acb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mOnActionListener != null) {
                        mOnActionListener.onCheckedChanged(data, b);
                    }
                }
            });
        }else {
            viewHolder.acb.setVisibility(View.GONE);
        }
        if (data instanceof Note) {
            viewHolder.civ.setVisibility(View.GONE);
            viewHolder.tvTitle.setText(((Note) data).getTitle());
            viewHolder.tvContent.setText(((Note) data).getDiscribe());
            Media m = NetResultUtil.getFirstImage((Note) data);
            if (m != null) {
                Glide.with(viewHolder.iv)
                        .load(m.getContent())
                        .into(viewHolder.iv);
            }
        }else if (data instanceof Course) {
            viewHolder.civ.setVisibility(View.GONE);
            viewHolder.tvTitle.setText(((Course) data).getTitle());
            viewHolder.tvContent.setText(((Course) data).getContent());
            Glide.with(viewHolder.iv)
                    .load(((Course) data).getImage_src())
                    .into(viewHolder.iv);
        }else if (data instanceof Entry) {
            viewHolder.civ.setVisibility(View.GONE);
            viewHolder.tvTitle.setText(((Entry) data).getName());
            viewHolder.tvContent.setText(((Entry) data).getContent());
            Glide.with(viewHolder.iv)
                    .load(((Entry) data).getUrl())
                    .into(viewHolder.iv);
        }else if (data instanceof User) {
            viewHolder.iv.setVisibility(View.GONE);
            viewHolder.tvTitle.setText(((User) data).getAccount_name());
            viewHolder.tvContent.setText(((User) data).getSign());
            Glide.with(viewHolder.civ)
                    .load(((User) data).getImage_src())
                    .into(viewHolder.civ);
        }
    }

    public void setOnActionListener (OnActionListener onActionListener) {
        mOnActionListener = onActionListener;
    }

    public void setShowCheck(boolean show) {
        mShowCheck = show;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        ImageView iv;
        CircleImageView civ;
        AppCompatCheckBox acb;

        ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_search_title);
            tvContent = itemView.findViewById(R.id.tv_item_search_content);
            iv = itemView.findViewById(R.id.iv_item_search);
            civ = itemView.findViewById(R.id.civ_item_search);
            acb = itemView.findViewById(R.id.acb_item_search);
        }
    }

    public interface OnActionListener {
        void onCheckedChanged(Object obj, boolean checked);
    }
}
