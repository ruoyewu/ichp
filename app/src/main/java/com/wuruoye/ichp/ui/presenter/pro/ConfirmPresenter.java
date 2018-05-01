package com.wuruoye.ichp.ui.presenter.pro;

import com.wuruoye.ichp.base.App;
import com.wuruoye.ichp.ui.contract.pro.ConfirmContract;
import com.wuruoye.library.model.WConfig;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 09:47.
 * @Description :
 */

public class ConfirmPresenter extends ConfirmContract.Presenter {
    @Override
    public void requestUp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (isAvailable()) {
                        App.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().onResultUpload();
                            }
                        });
                    }
                } catch (final InterruptedException e) {
                    if (isAvailable()) {
                        App.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().onResultError(e.getMessage());
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public String generatePhotoName() {
        return WConfig.IMAGE_PATH + System.currentTimeMillis() + ".jpg";
    }
}
