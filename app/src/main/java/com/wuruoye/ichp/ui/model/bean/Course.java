package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Course {
    private int act_id;
    private float issue_date;
    private float hold_date;
    private String title;
    private int publisher;
    private String hold_addr;
    private String content;
    private String act_src;
    private String image_src;

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
}
