package com.wuruoye.ichp.ui.presenter;

import com.wuruoye.ichp.ui.contract.FoundContract;
import com.wuruoye.ichp.ui.model.bean.Course;

import java.util.Arrays;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public class DevFoundPresenter extends FoundContract.Presenter {

    @Override
    public void requestRecommendNote() {
        if (isAvailable()) {

        }
    }

    @Override
    public void requestRecommendEntry() {
        if (isAvailable()) {

        }
    }

    @Override
    public void requestRecommendCourse() {
        if (isAvailable()) {
            getView().onRecommendCourseResult(Arrays.asList(
                    new Course("梅花篆字", "author", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=fad26bf91adfa9ece9235e4503b99c66/1b4c510fd9f9d72a685819f8df2a2834349bbb12.jpg"),
                    new Course("成都糖画", "author", "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=ece7aa309258d109c4e3aeb4e963ab82/8b13632762d0f703a03c5c9208fa513d2797c5f6.jpg")
            ));
        }
    }
}
