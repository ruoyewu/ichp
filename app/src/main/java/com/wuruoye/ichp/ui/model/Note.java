package com.wuruoye.ichp.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Created : wuruoye
 * @Date : 2018/4/1.
 * @Description : 「记录」原型类
 */

public class Note implements Parcelable {
    private int rec_id;
    private String addr;
    private int recorder;
    private int appr_num;
    private float issue_date;
    private String url;
    private String describe;
    private String type;
    private int comm_num;
    private String title;
    private String labels_id_str;

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getRecorder() {
        return recorder;
    }

    public void setRecorder(int recorder) {
        this.recorder = recorder;
    }

    public int getAppr_num() {
        return appr_num;
    }

    public void setAppr_num(int appr_num) {
        this.appr_num = appr_num;
    }

    public float getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(float issue_date) {
        this.issue_date = issue_date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getComm_num() {
        return comm_num;
    }

    public void setComm_num(int comm_num) {
        this.comm_num = comm_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabels_id_str() {
        return labels_id_str;
    }

    public void setLabels_id_str(String labels_id_str) {
        this.labels_id_str = labels_id_str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rec_id);
        dest.writeString(this.addr);
        dest.writeInt(this.recorder);
        dest.writeInt(this.appr_num);
        dest.writeFloat(this.issue_date);
        dest.writeString(this.url);
        dest.writeString(this.describe);
        dest.writeString(this.type);
        dest.writeInt(this.comm_num);
        dest.writeString(this.title);
        dest.writeString(this.labels_id_str);
    }

    public Note() {
    }

    protected Note(Parcel in) {
        this.rec_id = in.readInt();
        this.addr = in.readString();
        this.recorder = in.readInt();
        this.appr_num = in.readInt();
        this.issue_date = in.readFloat();
        this.url = in.readString();
        this.describe = in.readString();
        this.type = in.readString();
        this.comm_num = in.readInt();
        this.title = in.readString();
        this.labels_id_str = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
