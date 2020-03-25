package com.example.youyaoqilite.ui.search

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.RetrofitRequest.YouYaoQiService
import com.example.youyaoqilite.adapter.GridAdapter
import com.example.youyaoqilite.adapter.RecyclerViewAdapter
import com.example.youyaoqilite.adapter.SearchGridAdapter
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.data.RankList
import com.example.youyaoqilite.databinding.FragmentSearchBinding
import com.example.youyaoqilite.ui.ranklist.RanklistFragment
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var fragBinding: FragmentSearchBinding
    private val cartoonList = arrayListOf<Cartoon>()
    private var viewParent : ViewGroup? = null
    private var pref = MyApplication.getContext().getSharedPreferences("history",MODE_PRIVATE)
    private var editor = MyApplication.getContext().getSharedPreferences("history",MODE_PRIVATE).edit()


    object handler{
        var listSize : Int = 0
        var mHandler : Handler = Handler{false}
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewParent = container
        fragBinding = FragmentSearchBinding.inflate(layoutInflater)

        init()
        initHandler()
        return fragBinding.root
    }

    private fun init(){
        fragBinding.searchButton.text = "搜索"
        fragBinding.searchEdit.hint = "镇魂"

        val layoutManager = LinearLayoutManager(activity)
        fragBinding.searchRecycler.layoutManager = layoutManager
        fragBinding.searchRecycler.adapter = RecyclerViewAdapter(cartoonList,this)

        //搜索前的fragment grid
        fragBinding.currentSearchGrid.adapter = SearchGridAdapter(MyApplication.getContext(),RanklistFragment.handler.textList)
        //搜索历史
        var search = pref.getString("historySearch","")
        var stringList  = search?.split('\n')
        fragBinding.historySearchGrid.adapter = SearchGridAdapter(MyApplication.getContext(),
            stringList as MutableList<String>?
        )

        fragBinding.searchButton.setOnClickListener{
            var searchContent = fragBinding.searchEdit.text.toString()
            var searchbefore = pref.getString("historySearch","")

            editor.putString("historySearch",searchContent + "\n" + searchbefore)
            editor.apply()
            cartoonList.clear()
            handler.listSize = 0
            initCartoonsWithRetrofit(searchContent)
        }
    }

    private fun initHandler(){
        handler.mHandler = Handler{
            if (it.what == 1){
                if(fragBinding.currentSearch.visibility != View.GONE){
                    fragBinding.currentSearch.visibility = View.GONE
                    fragBinding.currentSearchGrid.visibility = View.GONE
                    fragBinding.historySearch.visibility = View.GONE
                    fragBinding.historySearchGrid.visibility = View.GONE
                }
                fragBinding.searchRecycler.adapter!!.notifyDataSetChanged()
                handler.listSize = cartoonList.size
                Toast.makeText(MyApplication.getContext(),"一共为你找到${cartoonList.size}个作品", Toast.LENGTH_SHORT).show()
            }
            else if (it.what == 8){     //点击了搜索
                initCartoonsWithRetrofit(it.obj.toString())
                fragBinding.searchEdit.setText(it.obj.toString())
            }
            false
        }
    }

    private fun initCartoonsWithRetrofit(searchContent: String) {
        var retrofit  = Retrofit.Builder()
            .baseUrl("https://app.u17.com/v3/appV3_3/android/phone/") //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        //创建请求接口类
        var youyaoqiService = retrofit.create(YouYaoQiService::class.java)
        youyaoqiService.searchCartoon("xiaomi","7de42d2e","450010","MI+6","f5c9b6c9284551ad",searchContent).enqueue(object : retrofit2.Callback<RankList> {
            override fun onFailure(call: Call<RankList>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RankList>, response: Response<RankList>) {
                var ranklist : RankList? = response.body()
                for (comic in ranklist!!.data.returnData.comics){
                    var c0 = Cartoon(comic.name,comic.cover,comic.tags[0],comic.description,comic.comicId,"",false,false)
                    cartoonList.add(c0)
                }
                var message = Message()
                message.what = 1
                handler.mHandler.sendMessage(message)
            }
        })
    }
}
