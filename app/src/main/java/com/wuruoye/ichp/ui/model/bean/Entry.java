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
    private String image;
    private String content;
    private int editor;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entry_id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.content);
        dest.writeInt(this.editor);
    }

    public Entry() {
    }

    protected Entry(Parcel in) {
        this.entry_id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.content = in.readString();
        this.editor = in.readInt();
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
