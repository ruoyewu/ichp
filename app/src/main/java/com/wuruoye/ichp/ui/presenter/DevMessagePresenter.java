package com.wuruoye.ichp.ui.presenter;

import com.wuruoye.ichp.ui.contract.pro.MessageContract;
import com.wuruoye.ichp.ui.model.bean.Comment;
import com.wuruoye.ichp.ui.model.bean.Praise;
import com.wuruoye.ichp.ui.model.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wuruoye.ichp.ui.MessageListFragment.TYPE_COMMENT;
import static com.wuruoye.ichp.ui.MessageListFragment.TYPE_MESSAGE;
import static com.wuruoye.ichp.ui.MessageListFragment.TYPE_PRAISE;

/**
 * Created by wuruoye on 2018/2/5.
 * this file is to
 */

public class DevMessagePresenter extends MessageContract.Presenter {
    @Override
    public void requestData(User user, int type, boolean isAdd) {
        List<Object> dataList = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        if (type == TYPE_PRAISE) {
            data.addAll(Arrays.asList(
                    new Praise("from1", 1517825953570L, Praise.Type.NOTE, null),
                    new Praise("from2", 1517825953570L, Praise.Type.COURSE, null),
                    new Praise("from3", 1517825953570L, Praise.Type.ENTRY, null)
            ));
        }else if (type == TYPE_COMMENT) {
            data.addAll(Arrays.asList(
                    new Comment("from1", 1517825953570L, Comment.Type.NOTE, null),
                    new Comment("from2", 1517825953570L, Comment.Type.COURSE, null),
                    new Comment("from3", 1517825953570L, Comment.Type.ENTRY, null)
            ));
        } else if (type == TYPE_MESSAGE) {

        }

        for (int i = 0; i < 5; i++) {
            dataList.addAll(data);
        }

        if (isAvailable()) {
            getView().onDataResult(dataList, isAdd);
        }
    }
}
