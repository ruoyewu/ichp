package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.ui.model.bean.User;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class UserInfoActivity extends BaseActivity {
    public static final String[] ITEM_TITLE = { "TA发布的活动", "TA发布的记录" };

    private CircleImageView civ;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvIntro;
    private TextView tvFocus;
    private TextView tvFocused;
    private TabLayout tl;
    private ViewPager vp;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = new User("name", "intro", "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=f900350f2a34349b600b66d7a8837eab/7e3e6709c93d70cfe7ea7e18fadcd100baa12b97.jpg");
    }

    @Override
    public void initView() {
        civ = findViewById(R.id.civ_user_info);
        ivBack = findViewById(R.id.iv_user_info_back);
        tvTitle = findViewById(R.id.tv_user_info_name);
        tvIntro = findViewById(R.id.tv_user_info_intro);
        tvFocus = findViewById(R.id.tv_user_info_focus);
        tvFocused = findViewById(R.id.tv_user_info_focused);
        tl = findViewById(R.id.tl_user_info);
        vp = findViewById(R.id.vp_user_info);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        Glide.with(this)
                .load(mUser.getImage())
                .into(civ);
        Glide.with(this)
                .load(mUser.getImage())
                .into(ivBack);
        tvTitle.setText(mUser.getName());
        tvIntro.setText(mUser.getIntro());
        tvFocus.setText("关注");
        tvFocused.setText("被关注");
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mUser);
            bundle.putInt("type", i * 2);
            Fragment fragment = new UserInfoListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }
}
