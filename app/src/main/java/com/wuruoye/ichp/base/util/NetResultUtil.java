package com.wuruoye.ichp.base.util;

import com.google.gson.Gson;

import org.json.JSONArray;
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
}
