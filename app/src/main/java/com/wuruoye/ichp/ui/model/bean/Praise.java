package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class Praise {
    public enum Type {
        NOTE,
        COURSE,
        ENTRY
    }

    private String from;
    private long time;
    private Type type;
    // must instance of Note, Course or Entry
    private Object origin;

    public Praise(String from, long time, Type type, Object origin) {
        this.from = from;
        this.time = time;
        this.type = type;
        this.origin = origin;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }
}
