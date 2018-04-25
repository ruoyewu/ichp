package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.CourseContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/3/31.
 * @Description :
 */

public class CoursePresenter extends CourseContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestCourse(final boolean isAdd) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        WNet.postInBackGround(Api.INSTANCE.getGET_ALL_ACT(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Course> courseList = NetResultUtil.parseDataList(s, Course.class);
                        Collections.reverse(courseList);
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
                        getView().onResultCourse(courseList, isAdd);
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
}
