package com.example.youyaoqilite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.youyaoqilite.R
import com.example.youyaoqilite.RetrofitRequest.YouYaoQiService
import com.example.youyaoqilite.data.ImageItem
import com.example.youyaoqilite.data.ImagesObtain
import com.example.youyaoqilite.databinding.ActivityReadBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class ReadActivity : AppCompatActivity() {

    private lateinit var fragBinding: ActivityReadBinding
    private var imageList = ArrayList<ImageItem>()
    private var chapterIdList = ArrayList<String>()
    private var chapterId = String()
    private var flag = true
    object handler{
        var mHandler : Handler = Handler{false}
    }

    private fun initHandler(){
        handler.mHandler = Handler{
            if (it.what == 1){
                initViewPager()
            }
            else if(it.what == 2){
                //initChaptersWithRetrofit(comicid)
            }
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragBinding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(fragBinding.root)
        chapterId  = intent.getStringExtra("chapter_id")
        chapterIdList = intent.getStringArrayListExtra("chapter_id_list")
        initHandler()
        if (chapterId != null)
        initImagesWithRetrofit(chapterId)
    }

    private fun initViewPager(){
        var viewpager = fragBinding.root.findViewById<ViewPager2>(R.id.read_pager)
        viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return imageList.size
            }

            override fun createFragment(position: Int): Fragment {
                if (position == imageList.size-3){
                    if (chapterIdList != null){
                        var chapterIdNext = chapterIdList.indexOf(chapterId)+1
                        if (chapterIdNext != chapterIdList.size){
                            chapterId = chapterIdList[chapterIdNext+1]
                            initImagesWithRetrofit(chapterIdList[chapterIdNext])
                        }
                    }
                }
                return ReadFragment(imageList[position].location)
            }
        }

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 很多情况下在页面切换完成后在此方法进行各种操作
            }
        })

        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                //refreshListFragment(listDataFragment)
                viewpager.adapter?.notifyDataSetChanged()
            }
        })
    }
    private fun initImagesWithRetrofit(chapterId : String){
        var retrofit  = Retrofit.Builder()
            .baseUrl("https://app.u17.com/v3/appV3_3/android/phone/") //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        //创建请求接口类
        var youyaoqiService = retrofit.create(YouYaoQiService::class.java)
        //https://app.u17.com/v3/appV3_3/android/phone/comic/chapterNew?come_from=huawei&serialNumber=880M890RC17411SC&v=5000100&model=JAT-AL00&chapter_id=9963&android_id=55ad025ee6967d57
        youyaoqiService.chapterImages("huawei","880M890RC17411SC","5000100","JAT-AL00",chapterId,"55ad025ee6967d57").enqueue(object : retrofit2.Callback<ImagesObtain> {
            override fun onFailure(call: Call<ImagesObtain>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ImagesObtain>, response: Response<ImagesObtain>) {
                var images : ImagesObtain? = response.body()
                if (images != null) {
                    for (imageDemo in images.data.returnData.image_list) {
                        imageList.add(imageDemo)
                    }
                }
                if (flag){
                    flag = false
                    var message = Message()
                    message.what = 1
                    handler.mHandler.sendMessage(message)
                }
            }
        })
    }
}
