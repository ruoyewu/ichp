package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class Entry {
    private String title;
    private String image;

    public Entry(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
