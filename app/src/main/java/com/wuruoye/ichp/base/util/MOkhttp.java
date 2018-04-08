package com.wuruoye.ichp.base.util;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.IWNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Created : wuruoye
 * @Date : 2018/4/2.
 * @Description :
 */

public class MOkhttp implements IWNet {
    private PARAM_TYPE mType;
    private static List<Cookie> mCookies;
    private static OkHttpClient mClient;

    public MOkhttp() {
        this.mType = PARAM_TYPE.FORM;
        mCookies = new ArrayList();
        mClient = (new OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS).cookieJar(new CookieJar() {
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                MOkhttp.mCookies.clear();
                if(MOkhttp.mCookies != null) {
                    MOkhttp.mCookies.addAll(cookies);
                }

            }

            public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                return MOkhttp.mCookies;
            }
        }).build();
    }

    public void setParamType(PARAM_TYPE type) {
        this.mType = type;
    }

    public void get(String url, ArrayMap<String, String> values, Listener<String> listener) {
        StringBuilder builder = new StringBuilder(url);
        if(values.size() > 0) {
            builder.append("?");
        }

        for (Object o : values.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        url = builder.toString();
        Request request = (new okhttp3.Request.Builder()).url(url).build();
        this.request(request, listener);
    }

    public void post(String url, ArrayMap<String, String> values, Listener<String> listener) {
        try {
            okhttp3.Request.Builder builder = (new okhttp3.Request.Builder()).url(url);
            if(this.mType == PARAM_TYPE.FORM) {
                builder.post(this.map2form(values));
            } else if(this.mType == PARAM_TYPE.JSON) {
                RequestBody body = RequestBody.create(MediaType.parse("json"), this.map2json(values));
                builder.post(body);
            }

            this.request(builder.build(), listener);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

    }

    public void uploadFile(String url, String key, String file, String type, Listener<String> listener) {
        File f = new File(file);
        if(f.isDirectory()) {
            listener.onFail("file " + file + " is a directory not file");
        } else if(!f.exists()) {
            listener.onFail("file " + file + " is not exists");
        } else {
            RequestBody body = (new okhttp3.MultipartBody.Builder()).addFormDataPart(key, f.getName(), RequestBody.create(MediaType.parse(type), f)).build();
            Request request = (new okhttp3.Request.Builder()).url(url).post(body).build();
            this.request(request, listener);
        }

    }

    public void downloadFile(String url, final String file, final Listener<String> listener) {
        Request request = (new okhttp3.Request.Builder()).url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    InputStream is = response.body().byteStream();
                    boolean result = com.wuruoye.library.util.FileUtil.writeInputStream(file, is);
                    if(result) {
                        listener.onSuccessful(file);
                    } else {
                        listener.onFail("error in write file");
                    }
                } else {
                    listener.onFail(response.message());
                }

            }
        });
    }

    public void uploadFile(String url, ArrayMap<String, String> values, ArrayMap<String, String> files, String type, Listener<String> listener) {
        okhttp3.MultipartBody.Builder builder = new okhttp3.MultipartBody.Builder();
        Iterator var7 = files.entrySet().iterator();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        while(var7.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var7.next();
            File file = new File(entry.getValue());
            if(file.isDirectory()) {
                listener.onFail("file " + entry.getValue() + " is a directory not file");
                return;
            }

            if(!file.exists()) {
                listener.onFail("file " + entry.getValue() + " is not exists");
                return;
            }

            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(type), file));
        }
        builder.setType(MediaType.parse("multipart/form-data; charset=utf-8"));

        Request request = (new okhttp3.Request.Builder()).url(url).post(builder.build()).build();
        this.request(request, listener);
    }

    private void request(Request request, final Listener<String> listener) {
        mClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    listener.onSuccessful(response.body().string());
                } else {
                    listener.onFail(response.message());
                }

            }
        });
    }

    private String map2json(ArrayMap<String, String> values) throws JSONException {
        JSONObject object = new JSONObject();
        Iterator var3 = values.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            object.put(entry.getKey(), entry.getValue());
        }

        return object.toString();
    }

    private FormBody map2form(ArrayMap<String, String> values) {
        okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
        Iterator var3 = values.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            builder.add(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }
}
