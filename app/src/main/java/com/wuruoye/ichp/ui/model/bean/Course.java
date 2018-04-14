package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Course implements Parcelable {
    private int act_id;
    private float issue_date;
    private float hold_date;
    private String title;
    private int publisher;
    private String hold_addr;
    private String content;
    private String act_src;
    private String image_src;
    private String labels_id_str;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabels_id_str() {
        return labels_id_str;
    }

    public void setLabels_id_str(String labels_id_str) {
        this.labels_id_str = labels_id_str;
    }

    public int getAct_id() {
        return act_id;
    }

    public void setAct_id(int act_id) {
        this.act_id = act_id;
    }

    public float getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(float issue_date) {
        this.issue_date = issue_date;
    }

    public float getHold_date() {
        return hold_date;
    }

    public void setHold_date(float hold_date) {
        this.hold_date = hold_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public String getHold_addr() {
        return hold_addr;
    }

    public void setHold_addr(String hold_addr) {
        this.hold_addr = hold_addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAct_src() {
        return act_src;
    }

    public void setAct_src(String act_src) {
        this.act_src = act_src;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.act_id);
        dest.writeFloat(this.issue_date);
        dest.writeFloat(this.hold_date);
        dest.writeString(this.title);
        dest.writeInt(this.publisher);
        dest.writeString(this.hold_addr);
        dest.writeString(this.content);
        dest.writeString(this.act_src);
        dest.writeString(this.image_src);
        dest.writeString(this.labels_id_str);
        dest.writeString(this.type);
    }

    public Course() {
    }

    protected Course(Parcel in) {
        this.act_id = in.readInt();
        this.issue_date = in.readFloat();
        this.hold_date = in.readFloat();
        this.title = in.readString();
        this.publisher = in.readInt();
        this.hold_addr = in.readString();
        this.content = in.readString();
        this.act_src = in.readString();
        this.image_src = in.readString();
        this.labels_id_str = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
