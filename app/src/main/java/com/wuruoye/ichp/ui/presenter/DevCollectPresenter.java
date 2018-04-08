package com.wuruoye.ichp.ui.presenter;

import com.wuruoye.ichp.ui.contract.CollectContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wuruoye.ichp.ui.CollectListFragment.TYPE_COURSE;
import static com.wuruoye.ichp.ui.CollectListFragment.TYPE_ENTRY;
import static com.wuruoye.ichp.ui.CollectListFragment.TYPE_NOTE;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public class DevCollectPresenter extends CollectContract.Presenter {
    @Override
    public void requestData(User user, int type, boolean isAdd) {
        List<Object> dataList = new ArrayList<>();
        if (type == TYPE_NOTE) {
            List<Note> data = Arrays.asList(
                    new Note("梅花篆字", "author", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=fad26bf91adfa9ece9235e4503b99c66/1b4c510fd9f9d72a685819f8df2a2834349bbb12.jpg"),
                    new Note("成都糖画", "author", "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=ece7aa309258d109c4e3aeb4e963ab82/8b13632762d0f703a03c5c9208fa513d2797c5f6.jpg"),
                    new Note("秸秆扎刻", "author", "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=f900350f2a34349b600b66d7a8837eab/7e3e6709c93d70cfe7ea7e18fadcd100baa12b97.jpg")
            );
            for (int i = 0; i < 5; i++) {
                dataList.addAll(data);
            }
        }else if (type == TYPE_COURSE) {
            List<Course> data = Arrays.asList(
                    new Course("梅花篆字", "author", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=fad26bf91adfa9ece9235e4503b99c66/1b4c510fd9f9d72a685819f8df2a2834349bbb12.jpg"),
                    new Course("成都糖画", "author", "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=ece7aa309258d109c4e3aeb4e963ab82/8b13632762d0f703a03c5c9208fa513d2797c5f6.jpg"),
                    new Course("秸秆扎刻", "author", "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=f900350f2a34349b600b66d7a8837eab/7e3e6709c93d70cfe7ea7e18fadcd100baa12b97.jpg")
            );
            for (int i = 0; i < 5; i++) {
                dataList.addAll(data);
            }
        }else if (type == TYPE_ENTRY) {

        }
        if (isAvailable()) {
            getView().onDataResult(dataList, isAdd);
        }
    }

    @Override
    public void remove(User user, Object data) {

    }

    @Override
    public void removeAll(User user, List<Object> dataList) {

    }
}
