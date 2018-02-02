package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class UserFragment extends BaseFragment {
    public static final String[] ITEM_TITLE = new String[] {
            "我的信息", "我的非遗活动", "我的非遗记录", "我的收藏", "我的消息", "我的非遗足迹",
            "非遗拾贝", "设置", "关于"
    };
    public static final int[] ITEM_ICON = new int[] {

    };

    private LinearLayout llUser;

    @Override
    public int getContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(@NotNull View view) {
        llUser = view.findViewById(R.id.ll_user);

        initItems();
    }

    private void initItems() {
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_user, llUser, false);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(finalI);
                }
            });
            ImageView iv = view.findViewById(R.id.iv_item_user);
            TextView tv = view.findViewById(R.id.tv_item_user);
            iv.setImageResource(R.drawable.ic_home);
            tv.setText(ITEM_TITLE[i]);
            llUser.addView(view);
        }
    }

    private void onItemClick(int position) {
        Toast.makeText(getContext(), ITEM_TITLE[position], Toast.LENGTH_SHORT).show();
    }
}
