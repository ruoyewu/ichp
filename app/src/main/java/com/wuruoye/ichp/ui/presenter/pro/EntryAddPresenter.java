package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.ui.contract.pro.EntryAddContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 16:52.
 * @Description :
 */

public class EntryAddPresenter extends EntryAddContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestAddEntry(String name, String content, String url) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("name", name);
        values.put("content", content);
        values.put("url", url);

        WNet.postInBackGround(Api.INSTANCE.getADD_ENTRY(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultAdd(true, null);
                        }
                    }else {
                        if (isAvailable()) {
                            getView().onResultAdd(false, jsonObject.getString("msg"));
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
    public void requestUpload(String path) {
        final ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        final ArrayMap<String, String> files = new ArrayMap<>();
        files.put("the_file", path);
        Tiny.getInstance().source(path)
                .asFile().compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                if (isSuccess) {
                    files.put("the_file", outfile);
                    WNet.uploadFileInBackground(Api.INSTANCE.getUPLOAD(), values, files, "image/*",
                            new Listener<String>() {
                                @Override
                                public void onSuccessful(String s) {
                                    try {
                                        JSONObject object = new JSONObject(s);
                                        boolean isOk;
                                        String url;
                                        if (object.getInt("code") == 0) {
                                            isOk = true;
                                            url = object.getString("addr");
                                        }else {
                                            isOk = false;
                                            url = object.getString("msg");
                                        }
                                        if (isAvailable()) {
                                            getView().onResultUpload(isOk, url);
                                        }
                                    } catch (JSONException e) {
                                        if (isAvailable()) {
                                            getView().onResultUpload(false, e.getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFail(String s) {
                                    if (isAvailable()) {
                                        getView().onResultUpload(false, s);
                                    }
                                }
                            });
                }else {
                    if (t != null) {
                        getView().onResultError(t.getMessage());
                    }
                }
            }
        });

    }
}
