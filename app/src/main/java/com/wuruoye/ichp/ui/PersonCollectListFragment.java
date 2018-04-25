package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.NormalRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.PersonCollectContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.PersonCollectPresenter;
import com.wuruoye.ichp.ui.util.IManagerView;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public class PersonCollectListFragment extends WBaseFragment<PersonCollectContract.Presenter>
        implements PersonCollectContract.View, IManagerView, BaseRVAdapter.OnItemClickListener<Object>,
        NormalRVAdapter.OnActionListener {

    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    private AlertDialog dlgDelete;

    private int mType;
    private List<Object> mToDeleteList = new LinkedList<>();
    private int mDelSuc = 0;
    private int mDelFai = 0;

    @Override
    public int getContentView() {
        return R.layout.layout_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");

        setPresenter(new PersonCollectPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        srl = view.findViewById(R.id.srl_layout);
        rv = view.findViewById(R.id.rv_layout);

        initLayout();
        initDlg();
        initRecyclerView();

        requestData();
    }

    private void initLayout() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    private void initDlg() {
        dlgDelete = new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setCancelable(false)
                .create();
    }

    private void initRecyclerView() {
        NormalRVAdapter adapter = new NormalRVAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnActionListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void requestData() {
        srl.setRefreshing(true);
        mPresenter.requestData(mType);
    }

    @Override
    public void onItemClick(Object data) {
        Intent intent;
        Bundle bundle = new Bundle();
        if (data instanceof Note) {
            intent = new Intent(getContext(), NoteShowActivity.class);
            bundle.putParcelable("note", (Note) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (data instanceof Course) {
            intent = new Intent(getContext(), CourseShowActivity.class);
            bundle.putParcelable("course", (Course) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (data instanceof Entry) {
            intent = new Intent(getContext(), EntryInfoActivity.class);
            bundle.putParcelable("entry", (Entry) data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void add() {

    }

    @Override
    public void remove() {
        ((NormalRVAdapter) rv.getAdapter()).setShowCheck(true);
    }

    @Override
    public void removeAll() {
        remove();
    }

    @Override
    public void submit() {
        if (mToDeleteList.size() > 0) {
            List<Integer> idList = new ArrayList<>();
            for (Object obj : mToDeleteList) {
                if (obj instanceof Note) {
                    idList.add(((Note) obj).getRec_id());
                }else if (obj instanceof Course) {
                    idList.add(((Course) obj).getAct_id());
                }else if (obj instanceof Entry) {
                    idList.add(((Entry) obj).getEntry_id());
                }
            }
            mPresenter.requestRemove(mType, idList);
        }
        ((NormalRVAdapter) rv.getAdapter()).setShowCheck(false);
        dlgDelete.show();
    }

    @Override
    public void onResultError(String error) {
        srl.setRefreshing(false);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultRemove(int id, boolean result) {
        if (result) {
            for (Object obj : mToDeleteList) {
                if (obj instanceof Note) {
                    if (((Note) obj).getRec_id() == id) {
                        ((NormalRVAdapter) rv.getAdapter()).removeData(obj);
                        mToDeleteList.remove(obj);
                        break;
                    }
                }else if (obj instanceof Course) {
                    if (((Course) obj).getAct_id() == id) {
                        ((NormalRVAdapter) rv.getAdapter()).removeData(obj);
                        mToDeleteList.remove(obj);
                        break;
                    }
                }else if (obj instanceof Entry) {
                    if (((Entry) obj).getEntry_id() == id) {
                        ((NormalRVAdapter) rv.getAdapter()).removeData(obj);
                        mToDeleteList.remove(obj);
                        break;
                    }
                }
            }

            mDelSuc ++;
        }else {
            mDelFai ++;
        }

        int total = mDelSuc + mToDeleteList.size();
        String message = "删除成功 " + mDelSuc + " / " + total + "\n"
                + "删除失败 " + mDelFai+ " / " + total;
        dlgDelete.setMessage(message);

        if (mDelFai == mToDeleteList.size()) {
            dlgDelete.dismiss();
            mToDeleteList.clear();
            mDelSuc = 0;
            mDelFai = 0;
        }
    }

    @Override
    public void onResultData(List<Object> dataList) {
        srl.setRefreshing(false);
        NormalRVAdapter adapter = (NormalRVAdapter) rv.getAdapter();
        adapter.setData(dataList);
    }

    @Override
    public void onCheckedChanged(Object obj, boolean checked) {
        if (checked) {
            mToDeleteList.add(obj);
        }else {
            mToDeleteList.remove(obj);
        }
    }
}
