package com.wuruoye.ichp.base.util;

import com.google.gson.Gson;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/1.
 * @Description : 解析网络请求的数据
 */

public class NetResultUtil {


    public static <T> List<T> parseDataList(String data, Class<T> classOf) throws Exception {
        List<T> result = new ArrayList<>();
        JSONObject object = new JSONObject(data);
        int code = object.getInt("code");
        if (code == 0) {
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                result.add(new Gson().fromJson(array.getString(i), classOf));
            }
            return result;
        }else {
            throw new Exception(object.getString("msg"));
        }
    }

    public static List<Note> net2localNote(List<Note> noteList) throws JSONException {
        for (Note n : noteList) {
            JSONArray array = new JSONArray(n.getUrl());
            String url = "", type = "";
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    String oj = array.getString(i);
                    JSONObject obj = new JSONObject(oj);
                    url += obj.getString("url") + ',';
                    type += obj.getString("type") + ',';
                }
                url = url.substring(0, url.length() - 1);
                type = type.substring(0, type.length() - 1);
            }
            n.setUrl(url);
            n.setType(type);
        }
        return noteList;
    }

    public static List<Course> net2localCourse(List<Course> courseList) throws JSONException {
        for (Course course : courseList) {
            JSONArray array = new JSONArray(course.getImage_src());
            String url = "", type = "";
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    String oj = array.getString(i);
                    JSONObject obj = new JSONObject(oj);
                    url += obj.getString("url") + ',';
                    type += obj.getString("type") + ',';
                }
                url = url.substring(0, url.length() - 1);
                type = type.substring(0, type.length() - 1);
            }
            course.setImage_src(url);
            course.setType(type);
        }
        return courseList;
    }
}
