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
import com.wuruoye.ichp.base.util.ShareUtil;
import com.wuruoye.ichp.ui.contract.pro.EntryInfoContract;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.presenter.pro.EntryInfoPresenter;
import com.wuruoye.library.ui.WBaseActivity;
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

public class EntryInfoActivity extends WBaseActivity<EntryInfoContract.Presenter>
        implements EntryInfoContract.View {
    public static final String[] ITEM_TITLE = { "词条动态", "词条活动" };
    public static final String[] ITEM_BOTTOM = {"返回", "编辑", "收藏", "分享"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_goleft_black, R.drawable.ic_edit,
            R.drawable.ic_star_white, R.drawable.ic_share};

    private TabLayout tl;
    private ViewPager vp;
    private ImageView ivBack;
    private CircleImageView civ;
    private TextView tvTitle;
    private TextView tvIntro;
    private LinearLayout llBottom;

    private TextView tvCollect;
    private ImageView ivCollect;

    private Entry mEntry;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mEntry = bundle.getParcelable("entry");

        setPresenter(new EntryInfoPresenter());
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_entry_info_back);
        civ = findViewById(R.id.civ_entry_info);
        tvTitle = findViewById(R.id.tv_entry_info_title);
        tvIntro = findViewById(R.id.tv_entry_info_intro);
        tl = findViewById(R.id.tl_entry_info);
        vp = findViewById(R.id.vp_entry_info);
        llBottom = findViewById(R.id.ll_entry_info_bottom);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        for (int i = 0; i < ITEM_BOTTOM.length; i++) {
            View view = llBottom.getChildAt(i);
            TextView tv = view.findViewById(R.id.tv_icon_text);
            ImageView iv = view.findViewById(R.id.iv_icon_text);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBottomClick(finalI);
                }
            });
            tv.setText(ITEM_BOTTOM[i]);
            iv.setImageResource(ICON_BOTTOM[i]);
            if (i == 2) {
                ivCollect = iv;
                tvCollect = tv;
            }
        }

        tvTitle.setText(mEntry.getName());
        tvIntro.setText(mEntry.getName());

        Glide.with(civ)
                .asBitmap()
                .load(mEntry.getUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@android.support.annotation.Nullable
                                                        GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        civ.setImageBitmap(resource);
                        ivBack.setImageBitmap(BitmapUtil.blur(getApplicationContext(), resource));
                        return false;
                    }
                })
                .submit();

        changeCollect(mEntry.isApprove());
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            bundle.putParcelable("entry", mEntry);
            Fragment fragment = new EntryInfoListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    private void changeCollect(boolean collect) {
        tvCollect.setText(collect ? "已收藏" : "收藏");
        tvCollect.setTextColor(collect ? Color.BLACK : Color.WHITE);
        ivCollect.setImageResource(collect ? R.drawable.ic_star_black : R.drawable.ic_star_white);
    }

    private void share() {
        String text = mEntry.getName() + "\n" + mEntry.getContent();
        ShareUtil.INSTANCE.shareText(text, this);
    }

    private void onBottomClick(int position) {
        switch (position) {
            case 0:
                onBackPressed();
                break;
            case 1:
                Intent intent = new Intent(this, EntryAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("modify", true);
                bundle.putParcelable("entry", mEntry);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                boolean collect = !mEntry.isApprove();
                mEntry.setApprove(collect);
                changeCollect(collect);
                mPresenter.requestCollectEntry(mEntry.getEntry_id(), collect);
                break;
            case 3:
                share();
                break;
        }
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
