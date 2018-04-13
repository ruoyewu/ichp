package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.ui.contract.pro.PersonInfoContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Created : wuruoye
 * @Date : 2018/4/13 16:03.
 * @Description :
 */

public class PersonInfoPresenter extends PersonInfoContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestUploadFile(String path) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        ArrayMap<String, String> files = new ArrayMap<>();
        files.put("the_file", path);
        WNet.uploadFileInBackground(Api.INSTANCE.getUPLOAD(), values, files, "image/*",
                new Listener<String>() {
                    @Override
                    public void onSuccessful(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            boolean isOk;
                            String url;
                            if (object.getInt("code") == 0) {
                                if (isAvailable()) {
                                    getView().onResultUploadFile(object.getString("addr"));
                                }
                            }else {
                                if (isAvailable()) {
                                    getView().onResultError(object.getString("msg"));
                                }
                            }
                        } catch (JSONException e) {
                            if (isAvailable()) {
                                getView().onResultError(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFail(String s) {
                        if (isAvailable()) {
                            getView().onResultError(s);
                        }
                    }
                });
    }

    @Override
    public void requestUpload(User user) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("telephone", user.getTelephone());
        values.put("name", user.getName());
        values.put("sign", user.getSign());
        values.put("image_src", user.getImage_src());

        WNet.postInBackGround(Api.INSTANCE.getSTORE_USER_INFO(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultUpload();
                        }
                    }else {
                        if (isAvailable()) {
                            getView().onResultError(object.getString("msg"));
                        }
                    }
                } catch (JSONException e) {
                    if (isAvailable()) {
                        getView().onResultError(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultError(s);
                }
            }
        });
    }
}
