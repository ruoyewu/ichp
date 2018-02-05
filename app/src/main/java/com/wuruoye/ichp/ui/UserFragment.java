package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.wuruoye.ichp.ui.UserAttentionActivity.TYPE_FOCUS;
import static com.wuruoye.ichp.ui.UserAttentionActivity.TYPE_FOCUSED;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class UserFragment extends BaseFragment implements View.OnClickListener{
    public static final String[] ITEM_TITLE = new String[] {
            "我的信息", "我的非遗记录", "我的收藏", "我的消息", "我的非遗足迹",
            "非遗拾贝", "设置", "关于"
    };
    public static final int[] ITEM_ICON = new int[] {

    };

    private CircleImageView civ;
    private TextView tvName;
    private TextView tvId;
    private TextView tvFocus;
    private TextView tvFocused;
    private LinearLayout llUser;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = new User("name", "intro", "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=f900350f2a34349b600b66d7a8837eab/7e3e6709c93d70cfe7ea7e18fadcd100baa12b97.jpg");
    }

    @Override
    public void initView(@NotNull View view) {
        llUser = view.findViewById(R.id.ll_user);
        civ = view.findViewById(R.id.civ_user);
        tvName = view.findViewById(R.id.tv_user_name);
        tvId = view.findViewById(R.id.tv_user_id);
        tvFocus = view.findViewById(R.id.tv_user_focus);
        tvFocused = view.findViewById(R.id.tv_user_focused);

        initLayout();
        initItems();
    }

    private void initLayout() {
        Glide.with(this)
                .load(mUser.getImage())
                .into(civ);
        tvName.setText(mUser.getName());
        tvFocused.setOnClickListener(this);
        tvFocus.setOnClickListener(this);
        civ.setOnClickListener(this);
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
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", mUser);
        Intent intent;
        switch (position) {
            case 0:
                // 我的信息
                intent = new Intent(getContext(), PersonInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 1:
                // 我的非遗记录
                intent = new Intent(getContext(), PersonNoteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                // 我的收藏
                intent = new Intent(getContext(), CollectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                // 我的消息
                break;
            case 4:
                // 我的非遗足迹
                break;
            case 5:
                // 非遗拾贝
                break;
            case 6:
                // 设置
                break;
            case 7:
                // 关于
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", mUser);
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_user_focus:
                bundle.putInt("type", TYPE_FOCUS);
                intent = new Intent(getContext(), UserAttentionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_user_focused:
                bundle.putInt("type", TYPE_FOCUSED);
                intent = new Intent(getContext(), UserAttentionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.civ_user:
                intent = new Intent(getContext(), UserInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }
}
