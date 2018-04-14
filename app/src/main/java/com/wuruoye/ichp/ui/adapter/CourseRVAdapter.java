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
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.library.util.DateUtil;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class CourseRVAdapter extends BaseRVAdapter<Course> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Course course = getData(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(course);
            }
        });
        String[] imgs = course.getImage_src().split(",");
        Glide.with(viewHolder.iv)
                .load(imgs[0])
                .into(viewHolder.iv);
        viewHolder.tvTitle.setText(course.getTitle());
        viewHolder.tvAuthor.setText(DateUtil.formatTime((long)course.getIssue_date() * 1000,
                "yyyy-MM-dd"));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tvTitle;
        private TextView tvAuthor;

        ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv_course_back);
            tvTitle = itemView.findViewById(R.id.tv_course_title);
            tvAuthor = itemView.findViewById(R.id.tv_course_author);
        }
    }
}
