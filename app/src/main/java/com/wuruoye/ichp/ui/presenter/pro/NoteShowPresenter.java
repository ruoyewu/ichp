package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.NoteShowContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Created : wuruoye
 * @Date : 2018/4/8.
 * @Description : 「展示记录页面」
 */

public class NoteShowPresenter extends NoteShowContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestPraise(int id) {

    }

    @Override
    public void requestComment(int id, String content) {

    }

    @Override
    public void requestCollect(int id) {

    }

    @Override
    public void requestEntry(String entry) {
        String[] entryList = entry.split(",");
        ArrayMap<String, String> values = new ArrayMap<>();
        for (String id : entryList) {
            values.clear();
            values.put("token", mUserCache.getToken());
            values.put("entry_id", id);
            WNet.postInBackGround(Api.INSTANCE.getGET_ENTRY(), values, new Listener<String>() {
                @Override
                public void onSuccessful(String s) {
                    try {
                        List<Entry> entries = NetResultUtil.parseDataList(s, Entry.class);
                        if (isAvailable()) {
                            getView().onResultEntry(entries);
                        }
                    } catch (Exception e) {
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

    @Override
    public void requestCommentList(int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", String.valueOf(id));
        WNet.postInBackGround(Api.INSTANCE.getCOMMENT_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    List<NoteComment> comments = NetResultUtil.parseDataList(s, NoteComment.class);
                    if (isAvailable()) {
                        getView().onResultNoteComment(comments);
                    }
                } catch (Exception e) {
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
    public String parseDate(float time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy / MM / dd HH : mm : ss",
                Locale.ENGLISH);
        return format.format(new Date((long)(time * 1000)));
    }

    @Override
    public List<Media> parseMedia(String url, String type) throws IllegalArgumentException{
        List<Media> mediaList = new ArrayList<>();
        String[] urls = url.split(","), types = type.split(",");
        int len;
        if ((len = urls.length) != types.length) {
            throw new IllegalArgumentException("url 与 type 不匹配");
        }
        if (urls[0].equals("")) {
            return mediaList;
        }
        for (int i = 0; i < len; i++) {
            mediaList.add(new Media(getType(types[i]), urls[i]));
        }
        return mediaList;
    }

    @Override
    public String[] parseLocation(String location) {
        return location.split(",");
    }

    private Media.Type getType(String type) {
        switch (type) {
            case "1":
                return Media.Type.IMAGE;
            case "2":
                return Media.Type.VIDEO;
            case "3":
                return Media.Type.RECORD;
        }
        return null;
    }
}
