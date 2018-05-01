package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.UserLevelContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.model.bean.UserPoint;
import com.wuruoye.ichp.ui.presenter.pro.UserLevelPresenter;
import com.wuruoye.library.ui.WBaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 10:47.
 * @Description :
 */

public class UserLevelActivity extends WBaseActivity<UserLevelContract.Presenter>
        implements View.OnClickListener, UserLevelContract.View {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civ;
    private TextView tvName;
    private TextView tvLevel;
    private TextView tvInfo;
    private TextView tvPermission;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_level;
    }

    @Override
    protected void initData(Bundle bundle) {

        setPresenter(new UserLevelPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        civ = findViewById(R.id.civ_level);
        tvLevel = findViewById(R.id.tv_level_level);
        tvName = findViewById(R.id.tv_level_name);
        tvInfo = findViewById(R.id.tv_level_info);
        tvPermission = findViewById(R.id.tv_level_permission);

        initLayout();
        mPresenter.requestUserInfo();
        mPresenter.requestLevelInfo();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("非遗拾贝");
        tvManager.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onResultError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUser(User user) {
        Glide.with(civ)
                .load(user.getImage_src())
                .into(civ);
        tvName.setText(user.getAccount_name());
    }

    @Override
    public void onResultLevel(UserPoint point) {
        String level = "用户等级：" + point.getLevel() + "\t" + point.getTitle();
        String info = "该用户：\n" +
                "\t点赞" + point.getApprN() + "次，获得" + point.getApprP() + "积分\n" +
                "\t评论" + point.getCommN() + "次，获得" + point.getCommP() + "积分\n" +
                "\t收藏" + point.getCollN() + "次，获得" + point.getCollP() + "积分\n" +
                "\t发布记录" + point.getRecN() + "次，获得" + point.getRecP() + "积分\n" +
                "\t添加/编辑词条" + point.getEntryN() + "次，获得" + point.getEntryP() + "积分\n" +
                "\n共获得" + point.getPoint() + "积分";
        String permission = "用户权限\n" + point.getAuthority();
        tvLevel.setText(level);
        tvInfo.setText(info);
        tvPermission.setText(permission);
    }
}
