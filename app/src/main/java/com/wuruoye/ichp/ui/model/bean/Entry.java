package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Entry implements Parcelable {
    private int entry_id;
    private String name;
    private String content;
    private int editor;
    private String url;
    private boolean isColl;

    public boolean isColl() {
        return isColl;
    }

    public void setColl(boolean coll) {
        isColl = coll;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEditor() {
        return editor;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Entry() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entry && ((Entry) obj).getEntry_id() == entry_id;
    }

    @Override
    public int hashCode() {
        return getEntry_id();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entry_id);
        dest.writeString(this.name);
        dest.writeString(this.content);
        dest.writeInt(this.editor);
        dest.writeString(this.url);
        dest.writeByte(this.isColl ? (byte) 1 : (byte) 0);
    }

    protected Entry(Parcel in) {
        this.entry_id = in.readInt();
        this.name = in.readString();
        this.content = in.readString();
        this.editor = in.readInt();
        this.url = in.readString();
        this.isColl = in.readByte() != 0;
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
