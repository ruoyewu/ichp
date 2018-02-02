package com.wuruoye.ichp.ui.model.bean;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class User {
    private String name;
    private String intro;
    private String image;

    public User(String name, String intro, String image) {
        this.name = name;
        this.intro = intro;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
