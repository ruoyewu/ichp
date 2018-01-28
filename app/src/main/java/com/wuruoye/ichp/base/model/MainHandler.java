package com.wuruoye.ichp.base.model;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (instance == null) {
            synchronized (MainHandler.class) {
                if (instance == null) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
