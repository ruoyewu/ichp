package com.wuruoye.ichp.ui.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Entry implements Parcelable {
    private String name;
    private String url;
    private String content;
    private int editor;
    private int entry_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.content);
        dest.writeInt(this.editor);
        dest.writeInt(this.entry_id);
    }

    public Entry() {
    }

    protected Entry(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.editor = in.readInt();
        this.entry_id = in.readInt();
    }

    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
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
