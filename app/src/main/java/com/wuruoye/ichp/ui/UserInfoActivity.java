package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.ui.contract.pro.UserInfoContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.UserInfoPresenter;
import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.util.BitmapUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.wuruoye.ichp.ui.contract.pro.UserAttentionContract.TYPE_ATTED;
import static com.wuruoye.ichp.ui.contract.pro.UserAttentionContract.TYPE_ATTEN;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class UserInfoActivity extends WBaseActivity<UserInfoContract.Presenter>
        implements UserInfoContract.View, View.OnClickListener {
    public static final String[] ITEM_TITLE = { "TA发布的记录", "TA发布的活动" };
    public static final String[] ITEM_BOTTOM = {"返回", "关注"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_goleft_white, R.drawable.ic_star_white};

    private CircleImageView civ;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvIntro;
    private TextView tvFocus;
    private TextView tvFocused;
    private TabLayout tl;
    private ViewPager vp;
    private LinearLayout llBottom;

    private TextView tvAttention;
    private ImageView ivAttention;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mUser = bundle.getParcelable("user");

        setPresenter(new UserInfoPresenter());
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
            if (i == 1) {
                ivAttention = iv;
                tvAttention = tv;
            }
        }

        tvFocused.setOnClickListener(this);
        tvFocus.setOnClickListener(this);

        tvTitle.setText(mUser.getAccount_name());
        tvIntro.setText(mUser.getSign());
        tvFocus.setText("关注\t" + mUser.getPayNum());
        tvFocused.setText("粉丝\t" + mUser.getBePaidNum());
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

        changeAttention(mUser.isConcern());
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

    private void changeAttention(boolean att) {
        tvAttention.setText(att ? "已关注" : "关注");
        tvAttention.setTextColor(att ? Color.BLACK : Color.WHITE);
        ivAttention.setImageResource(att ? R.drawable.ic_star_black : R.drawable.ic_star_white);
    }

    private void onBottomClick(int position) {
        if (position == 0) {
            onBackPressed();
        }else if (position == 1) {
            boolean att = !mUser.isConcern();
            mUser.setConcern(att);
            changeAttention(att);
            mPresenter.requestAttention(att, mUser.getUser_id());
        }
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_user_info_focus:
                intent = new Intent(this, UserAttentionActivity.class);
                bundle.putInt("type", TYPE_ATTEN);
                bundle.putInt("userId", mUser.getUser_id());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_user_info_focused:
                intent = new Intent(this, UserAttentionActivity.class);
                bundle.putInt("type", TYPE_ATTED);
                bundle.putInt("userId", mUser.getUser_id());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
