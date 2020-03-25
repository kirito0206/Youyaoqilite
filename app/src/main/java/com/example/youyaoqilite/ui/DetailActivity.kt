package com.example.youyaoqilite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.RetrofitRequest.YouYaoQiService
import com.example.youyaoqilite.adapter.ChapterAdapter
import com.example.youyaoqilite.adapter.RecyclerViewAdapter
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.data.Chapter
import com.example.youyaoqilite.data.Chapters
import com.example.youyaoqilite.databinding.ActivityDetailBinding
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {

    private lateinit var activityBinding : ActivityDetailBinding
    private var chapterList = ArrayList<Chapter>()
    private lateinit var comicid : String
    object handler{
        var mHandler : Handler = Handler{false}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(activityBinding.root)

        initView()
        initHandler()
        initChaptersWithRetrofit(comicid)
    }

    private fun initView(){
        var cover : String? = intent.getStringExtra("cartoon_cover")
        var name : String? = intent.getStringExtra("cartoon_name")
        var tags : String? = intent.getStringExtra("cartoon_tags")
        var description : String? = intent.getStringExtra("cartoon_description")
        comicid = intent.getStringExtra("cartoon_id")
        var showText = StringBuilder("")
        showText.appendln("作品名称：$name")
        showText.appendln("标签内容：$tags")
        showText.appendln("作品简介：$description")
        activityBinding.detailText.text = showText
        Glide.with(this).load(cover).error(R.mipmap.ic_launcher).into(activityBinding.detailCover)

        setSupportActionBar(activityBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activityBinding.collapsingToolbar.title = name
        //按钮设置跳跃至历史记录
        activityBinding.floutButton.setOnClickListener(View.OnClickListener {
            if (chapterList.size != 0){
                var intentTo = Intent()
                intentTo.setClass(MyApplication.getContext(),ReadActivity::class.java)
                if (name != null){
                    var list = CartoonDaoOpe.getInstance().queryHistoryForName(MyApplication.getContext(),name)
                    if (list != null && list.size != 0)
                        intentTo.putExtra("chapter_id",list[0].chapterid)
                    else
                        intentTo.putExtra("chapter_id",chapterList[0].chapter_id)
                }
                intentTo.putStringArrayListExtra("chapter_id_list",chapterToString(chapterList))
                intentTo.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                MyApplication.getContext().startActivity(intentTo)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        activityBinding.detailChapterRecycler.layoutManager = layoutManager
        activityBinding.detailChapterRecycler.adapter = ChapterAdapter(chapterList,this, Cartoon(name,cover,tags,description,comicid,"",false,false))
    }

    private fun initHandler(){
        handler.mHandler = Handler{
            if (it.what == 1){
                activityBinding.detailChapterRecycler.adapter!!.notifyDataSetChanged()
            }
            else if(it.what == 2){
                initChaptersWithRetrofit(comicid)
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {finish()
                return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initChaptersWithRetrofit(comicid: String) {
        var retrofit  = Retrofit.Builder()
            .baseUrl("https://app.u17.com/v3/appV3_3/android/phone/") //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        //创建请求接口类
        var youyaoqiService = retrofit.create(YouYaoQiService::class.java)
        youyaoqiService.detailChapters("xiaomi","7de42d2e","450010","MI+6","f5c9b6c9284551ad",comicid).enqueue(object : retrofit2.Callback<Chapters> {
            override fun onFailure(call: Call<Chapters>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Chapters>, response: Response<Chapters>) {
                var chapters : Chapters? = response.body()
                if (chapters != null) {
                    for (chapterDemo in chapters.data.returnData.chapter_list) {
                        chapterList.add(chapterDemo)
                        //Log.d("456",chapterDemo.name+chapterDemo.smallPlaceCover+chapterDemo.image_total+"*"+chapterDemo.chapter_id)
                    }
                }
                var message = Message()
                message.what = 1
                handler.mHandler.sendMessage(message)
            }
        })
    }

    private fun chapterToString(chapterList: ArrayList<Chapter>) : ArrayList<String>{
        var chapterIdList = ArrayList<String>()
        for (chapter in chapterList){
            chapterIdList.add(chapter.chapter_id)
        }
        return chapterIdList
    }
}
