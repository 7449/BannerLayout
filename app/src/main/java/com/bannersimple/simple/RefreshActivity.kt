package com.bannersimple.simple

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.bannersimple.R
import com.bannersimple.bean.SimpleData
import com.bannersimple.refresh.Api
import com.bannersimple.refresh.ListModel
import com.bannersimple.refresh.RefreshAdapter
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import java.util.*


/**
 * by y on 2017/3/8.
 */

class RefreshActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, RxNetWorkListener<List<ListModel>>, Runnable {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: RefreshAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.post(this)
        adapter = RefreshAdapter(ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    override fun onRefresh() {
        RxNetWork.getInstance().cancel(javaClass.simpleName)
        RxNetWork
                .getInstance()
                .setBaseUrl(Api.ZL_BASE_API)
                .getApi(javaClass.simpleName, RxNetWork.observable(Api.ZLService::class.java).getList("daily", 20, 0), this)
    }


    override fun onNetWorkStart() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onNetWorkError(e: Throwable) {
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(applicationContext, "net work error", Toast.LENGTH_SHORT).show()
    }

    override fun onNetWorkComplete() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onNetWorkSuccess(data: List<ListModel>) {
        adapter.clear()
        adapter.addBanner(SimpleData.initModel())
        adapter.addAll(data)
    }


    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.getInstance().cancel(javaClass.simpleName)
    }

    override fun run() {
        onRefresh()
    }
}
