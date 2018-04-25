package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.UserContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.UserPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.wuruoye.ichp.ui.UserAttentionActivity.TYPE_FOCUS;
import static com.wuruoye.ichp.ui.UserAttentionActivity.TYPE_FOCUSED;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class UserFragment extends WBaseFragment<UserContract.Presenter>
        implements View.OnClickListener, UserContract.View{
    public static final String[] ITEM_TITLE = new String[] {
            "我的信息", "我的非遗记录", "我的收藏", "我的消息", "我的非遗足迹",
            "非遗拾贝", "设置", "关于"
    };
    public static final int[] ITEM_ICON = new int[] {

    };
    public final int USER_INFO = hashCode() % 10000;

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
        setPresenter(new UserPresenter());
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

        mPresenter.requestUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_INFO && resultCode == RESULT_OK) {
            mUser = data.getParcelableExtra("user");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initLayout() {
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
        Intent intent;
        switch (position) {
            case 0:
                // 我的信息
                intent = new Intent(getContext(), PersonInfoActivity.class);
                bundle.putParcelable("user", mUser);
                intent.putExtras(bundle);
                startActivityForResult(intent, USER_INFO);
                break;
            case 1:
                // 我的非遗记录
                intent = new Intent(getContext(), PersonNoteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                // 我的收藏
                intent = new Intent(getContext(), PersonCollectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                // 我的消息
                intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 4:
                // 我的非遗足迹
                intent = new Intent(getContext(), PersonTraceActivity.class);
                startActivity(intent);
                break;
            case 5:
                // 非遗拾贝
                break;
            case 6:
                // 设置
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case 7:
                // 关于
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
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

    @Override
    public void onResultError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUserInfo(User user) {
        mUser = user;
        tvName.setText(user.getAccount_name());
        tvId.setText(String.valueOf(user.getUser_id()));
        Glide.with(civ)
                .load(user.getImage_src())
                .into(civ);
    }
}
