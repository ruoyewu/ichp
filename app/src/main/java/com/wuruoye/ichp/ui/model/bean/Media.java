package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class Media {
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
}
