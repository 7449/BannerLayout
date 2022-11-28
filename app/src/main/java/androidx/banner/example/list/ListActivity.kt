package androidx.banner.example.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.banner.example.databinding.ActivityRecyclerViewBinding
import androidx.banner.example.newModel
import androidx.banner.example.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import io.reactivex.network.SimpleRxNetOptionFactory
import io.reactivex.network.getApi
import retrofit2.converter.gson.GsonConverterFactory

/**
 * by y on 2017/3/8.
 */
class ListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    RxNetWorkListener<ListModel>, Runnable {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val viewBind by viewBinding(ActivityRecyclerViewBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxNetWork.initialization(
            SimpleRxNetOptionFactory(
                Api.ZL_BASE_API,
                GsonConverterFactory.create()
            )
        )
        title = "RecyclerView Example"
        viewBind.refreshLayout.setOnRefreshListener(this)
        viewBind.refreshLayout.post(this)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        viewBind.recyclerView.adapter = adapter
    }

    override fun onRefresh() {
        RxNetWork.cancelTag(javaClass.simpleName)
        RxNetWork
            .observable(Api.ZLService::class.java)
            .getList()
            .getApi(javaClass.simpleName, this)
    }

    override fun onNetWorkStart() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun onNetWorkError(e: Throwable) {
        viewBind.refreshLayout.isRefreshing = false
        Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
    }

    override fun onNetWorkComplete() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun onNetWorkSuccess(data: ListModel) {
        adapter.clear()
        adapter.addBanner(newModel())
        adapter.addAll(data.topStories)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun run() {
        onRefresh()
    }
}
