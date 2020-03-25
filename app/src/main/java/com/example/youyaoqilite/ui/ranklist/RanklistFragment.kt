package com.example.youyaoqilite.ui.ranklist

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.youyaoqilite.RetrofitRequest.YouYaoQiService
import com.example.youyaoqilite.adapter.RecyclerViewAdapter
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.data.RankList
import com.example.youyaoqilite.databinding.FragmentRanklistBinding
import kotlinx.android.synthetic.main.fragment_ranklist.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RanklistFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{

    private lateinit var fragBinding: FragmentRanklistBinding
    private val cartoonList = arrayListOf<Cartoon>()
    object handler{
        var page : Int = 0
        var textList = ArrayList<String>()
        var mHandler : Handler = Handler{false}
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /*val recyclerView : RecyclerView = root.findViewById(R.id.ranklist_recycleview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        initCartoonsWithRetrofit()

        var swipe :SwipeRefreshLayout = root.findViewById(R.id.swiperefresh_layout)
        swipe.setOnRefreshListener(this)*/
        fragBinding = FragmentRanklistBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(activity)
        fragBinding.ranklistRecycleview.layoutManager = layoutManager
        fragBinding.ranklistRecycleview.adapter = RecyclerViewAdapter(cartoonList,this)
        initHandler()
        initCartoonsWithRetrofit("1")
        //下拉刷新
        fragBinding.swiperefreshLayout.setOnRefreshListener(this)
        return fragBinding.root
    }

    private fun initHandler(){
        handler.mHandler = Handler{
            if (it.what == 1){
                Log.d("123",cartoonList.size.toString())
                fragBinding.ranklistRecycleview.adapter!!.notifyDataSetChanged()
            }
            else if(it.what == 2){
                initCartoonsWithRetrofit(handler.page.toString())
            }
            false
        }
    }

    private fun initCartoonsWithRetrofit(page: String) {
        var retrofit  = Retrofit.Builder()
            .baseUrl("https://app.u17.com/v3/appV3_3/android/phone/") //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        //创建请求接口类
        var youyaoqiService = retrofit.create(YouYaoQiService::class.java)
        youyaoqiService.getRankListItem("total","2","xiaomi","7de42d2e","450010","MI+6","f5c9b6c9284551ad",page).enqueue(object : retrofit2.Callback<RankList> {
            override fun onFailure(call: Call<RankList>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RankList>, response: Response<RankList>) {
                var ranklist : RankList? = response.body()
                for (comic in ranklist!!.data.returnData.comics){
                    var c0 = Cartoon(comic.name,comic.cover,comic.tags[0],comic.description,comic.comicId,"",false,false)
                    cartoonList.add(c0)
                    if (handler.textList.size <10)
                        handler.textList.add(comic.name)
                }
                var message = Message()
                message.what = 1
                handler.mHandler.sendMessage(message)
            }
        })
    }

    override fun onRefresh() {
        //关闭下拉刷新进度条
        swiperefresh_layout.isRefreshing = false
        cartoonList.clear()
        handler.page = 0
        initCartoonsWithRetrofit("1")
    }
}
