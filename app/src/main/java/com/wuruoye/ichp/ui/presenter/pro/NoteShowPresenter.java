package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.NoteShowContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    public void requestPraise(int id, boolean praise) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", String.valueOf(id));
        String url = praise ? Api.INSTANCE.getPRAISE_REC() : Api.INSTANCE.getREMOVE_APPR_REC();
        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultError("操作成功");
                        }
                    } else {
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
    public void requestComment(int id, String content) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", String.valueOf(id));
        values.put("content", content);
        WNet.postInBackGround(Api.INSTANCE.getCOMMENT_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultUpComment(true, "评论成功");
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
    public void requestCollect(int id, boolean coll) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", String.valueOf(id));
        String url = coll ? Api.INSTANCE.getCOLLECT_RECORD() : Api.INSTANCE.getDELETE_COLL_REC();
        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultError("操作成功");
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
    public void requestCommentList(int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", String.valueOf(id));
        WNet.postInBackGround(Api.INSTANCE.getGET_COMMENT_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    List<NoteComment> comments = NetResultUtil.parseDataList(s, NoteComment.class);
                    Collections.reverse(comments);
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
    public void requestEntryList(String str) {
        final String[] ss = str.split(",");
        final List<Entry> entryList = new ArrayList<>();
        for (String s : ss) {
            ArrayMap<String, String> values = new ArrayMap<>();
            values.put("token", mUserCache.getToken());
            values.put("entry_id", s);
            WNet.postInBackGround(Api.INSTANCE.getGET_ENTRY(), values, new Listener<String>() {
                @Override
                public void onSuccessful(String s) {
                    try {
                        List<Entry> entries = NetResultUtil.parseDataList(s, Entry.class);
                        entryList.addAll(entries);
                        if (entryList.size() == ss.length) {
                            if (isAvailable()) {
                                getView().onResultEntry(entryList);
                            }
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
    public void requestUserInfo(int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("user_id", String.valueOf(id));
        WNet.postInBackGround(Api.INSTANCE.getUSER_INFO(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    List<User> userList = NetResultUtil.parseDataList(s, User.class);
                    if (userList.size() == 0) {
                        throw new Exception("user not found");
                    }
                    if (isAvailable()) {
                        getView().onResultUserInfo(userList.get(0));
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
    public void requestNoteInfo(int noteId) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", "" + noteId);

        WNet.postInBackGround(Api.INSTANCE.getGET_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Note> notes = NetResultUtil.net2localNote(NetResultUtil
                                .parseDataList(s, Note.class));
                        if (notes.size() > 0) {
                            getView().onResultNoteInfo(notes.get(0));
                        }else {
                            getView().onResultError("记录不存在");
                        }
                    } catch (Exception e) {
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
    public void requestModifyEntry(int recId, List<Entry> entryList) {
        if (entryList.size() > 0) {
            StringBuilder sEntry = new StringBuilder();
            sEntry.append(entryList.remove(0).getEntry_id());
            for (Entry entry : entryList) {
                sEntry.append(",").append(entry.getEntry_id());
            }
            ArrayMap<String, String> values = new ArrayMap<>();
            values.put("token", mUserCache.getToken());
            values.put("rec_id", "" + recId);
            values.put("labels_id_str", sEntry.toString());

            WNet.postInBackGround(Api.INSTANCE.getMODIFY_REC_LAB(), values, new Listener<String>() {
                @Override
                public void onSuccessful(String s) {
                    if (isAvailable()) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (obj.getInt("code") == 0) {
                                getView().onResultModifyEntry(true, null);
                            }else {
                                getView().onResultModifyEntry(false, obj.getString("msg"));
                            }
                        } catch (JSONException e) {
                            getView().onResultModifyEntry(false, e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String s) {
                    if (isAvailable()) {
                        getView().onResultModifyEntry(false, s);
                    }
                }
            });
        }else {
            getView().onResultError("词条数量不能为0");
        }
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
    public int getUserId() {
        return mUserCache.getUserId();
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
