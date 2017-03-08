package com.bannersimple.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bannerlayout.model.BannerModel;
import com.bannersimple.R;
import com.rxnetwork.manager.RxNetWork;
import com.rxnetwork.manager.RxNetWorkListener;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/8.
 */

public class RefreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RxNetWorkListener<List<ListModel>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RefreshAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        adapter = new RefreshAdapter(new ArrayList<ListModel>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        RxNetWork
                .getInstance()
                .setBaseUrl(Api.ZL_BASE_API)
                .getApi(RxNetWork.observable(Api.ZLService.class).getList("daily", 20, 0), this);
    }


    @Override
    public void onNetWorkStart() {
        RxNetWork.getInstance().clearSubscription();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onNetWorkError(Throwable e) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "net work error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetWorkCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetWorkSuccess(List<ListModel> data) {
        adapter.clear();
        adapter.addBanner(initBannerData());
        adapter.addAll(data);
    }

    /**
     * Comes with the Model class, the use of network data
     */
    private List<BannerModel> initBannerData() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "Shame it ~"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }
}
