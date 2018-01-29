package com.wuruoye.ichp.base.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter {
    private List<T> mDataList;
    private OnItemClickListener<T> mListener;
    private OnItemLongClickListener<T> mLongListener;

    public BaseRVAdapter() {
        mDataList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mLongListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addData(List<T> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setData(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        mDataList.remove(data);
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return mDataList.get(position);
    }

    protected void onItemClick(T data) {
        if (mListener != null) {
            mListener.onItemClick(data);
        }
    }

    protected void onItemLongClick(T data) {
        if (mLongListener != null) {
            mLongListener.onItemLongClick(data);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T model);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(T model);
    }
}
