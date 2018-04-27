package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Created : wuruoye
 * @Date : 2018/4/8.
 * @Description : 记录评论 model
 */

public class NoteComment implements Parcelable {
    private int comm_rec_id;
    private float comm_date;
    private String content;
    private int commer;
    private int rec_id;
    private int appr_num;
    private String account_name;
    private String image_src;

    public int getComm_rec_id() {
        return comm_rec_id;
    }

    public void setComm_rec_id(int comm_rec_id) {
        this.comm_rec_id = comm_rec_id;
    }

    public float getComm_date() {
        return comm_date;
    }

    public void setComm_date(float comm_date) {
        this.comm_date = comm_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommer() {
        return commer;
    }

    public void setCommer(int commer) {
        this.commer = commer;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public int getAppr_num() {
        return appr_num;
    }

    public void setAppr_num(int appr_num) {
        this.appr_num = appr_num;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public NoteComment() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.comm_rec_id);
        dest.writeFloat(this.comm_date);
        dest.writeString(this.content);
        dest.writeInt(this.commer);
        dest.writeInt(this.rec_id);
        dest.writeInt(this.appr_num);
        dest.writeString(this.account_name);
        dest.writeString(this.image_src);
    }

    protected NoteComment(Parcel in) {
        this.comm_rec_id = in.readInt();
        this.comm_date = in.readFloat();
        this.content = in.readString();
        this.commer = in.readInt();
        this.rec_id = in.readInt();
        this.appr_num = in.readInt();
        this.account_name = in.readString();
        this.image_src = in.readString();
    }

    public static final Creator<NoteComment> CREATOR = new Creator<NoteComment>() {
        @Override
        public NoteComment createFromParcel(Parcel source) {
            return new NoteComment(source);
        }

        @Override
        public NoteComment[] newArray(int size) {
            return new NoteComment[size];
        }
    };
}
