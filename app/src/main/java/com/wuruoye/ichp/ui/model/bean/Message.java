package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class Message {
    private User from;
    private User to;
    private String content;
    private long time;

    public Message(User from, String content, long time) {
        this.from = from;
        this.content = content;
        this.time = time;
    }

    public Message(User from, User to, String content, long time) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.time = time;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
