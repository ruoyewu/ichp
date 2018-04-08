package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class Media implements Parcelable {
    private Type type;
    private String content;

    public enum Type {
        IMAGE,
        VIDEO,
        RECORD
    }

    public Media(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.content);
    }

    protected Media(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
