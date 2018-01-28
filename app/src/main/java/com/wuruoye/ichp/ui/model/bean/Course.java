package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Course {
    private String title;
    private String author;
    private String image;

    public Course(String title, String author, String image) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
