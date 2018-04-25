package com.wuruoye.ichp.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.util.BitmapUtil;

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
    public static final String[] ITEM_TITLE = { "TA发布的记录", "TA发布的活动" };
    public static final String[] ITEM_BOTTOM = {"返回", "关注"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_goleft_white, R.drawable.ic_star};

    private CircleImageView civ;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvIntro;
    private TextView tvFocus;
    private TextView tvFocused;
    private TabLayout tl;
    private ViewPager vp;
    private LinearLayout llBottom;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");
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
        llBottom = findViewById(R.id.ll_user_info_bottom);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            View view = llBottom.getChildAt(i);
            ImageView iv = view.findViewById(R.id.iv_icon_text);
            TextView tv = view.findViewById(R.id.tv_icon_text);
            iv.setImageResource(ICON_BOTTOM[i]);
            tv.setText(ITEM_BOTTOM[i]);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBottomClick(finalI);
                }
            });
        }

        tvTitle.setText(mUser.getAccount_name());
        tvIntro.setText(mUser.getSign());
        tvFocus.setText("关注");
        tvFocused.setText("被关注");
        Glide.with(civ)
                .asBitmap()
                .load(mUser.getImage_src())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@android.support.annotation.Nullable GlideException e,
                                                Object model, Target<Bitmap> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        civ.setImageBitmap(resource);
                        ivBack.setImageBitmap(BitmapUtil.blur(getApplicationContext(), resource));
                        return false;
                    }
                })
                .submit();
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mUser);
            bundle.putInt("type", i + 1);
            Fragment fragment = new UserInfoListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    private void onBottomClick(int position) {
        if (position == 0) {
            onBackPressed();
        }else if (position == 1) {
            Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
        }
    }
}
