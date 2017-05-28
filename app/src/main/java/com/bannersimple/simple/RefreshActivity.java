package com.bannersimple.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bannersimple.R;
import com.bannersimple.bean.SimpleData;
import com.bannersimple.refresh.Api;
import com.bannersimple.refresh.ListModel;
import com.bannersimple.refresh.RefreshAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.network.manager.RxNetWork;
import io.reactivex.network.manager.RxNetWorkListener;

/**
 * by y on 2017/3/8.
 */

public class RefreshActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        RxNetWorkListener<List<ListModel>>, Runnable {


    private SwipeRefreshLayout swipeRefreshLayout;
    private RefreshAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this);

        adapter = new RefreshAdapter(new ArrayList<ListModel>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        RxNetWork.getInstance().cancel(RxNetWork.TAG);
        RxNetWork
                .getInstance()
                .setBaseUrl(Api.ZL_BASE_API)
                .getApi(RxNetWork.TAG,
                        RxNetWork.observable(Api.ZLService.class).getList("daily", 20, 0), this);
    }


    @Override
    public void onNetWorkStart() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onNetWorkError(Throwable e) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "net work error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetWorkComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetWorkSuccess(List<ListModel> data) {
        adapter.clear();
        adapter.addBanner(SimpleData.initModel());
        adapter.addAll(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clearBanner();
        RxNetWork.getInstance().cancel(RxNetWork.TAG);
    }

    @Override
    public void run() {
        onRefresh();
    }
}
