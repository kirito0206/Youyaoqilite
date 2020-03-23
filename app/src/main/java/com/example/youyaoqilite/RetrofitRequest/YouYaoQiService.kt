package com.example.youyaoqilite.RetrofitRequest

import com.example.youyaoqilite.data.Chapters
import com.example.youyaoqilite.data.ImagesObtain
import com.example.youyaoqilite.data.RankList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouYaoQiService {
    @GET("list/getRankComicList")
    fun getRankListItem(@Query("period") period : String, @Query("type") type : String, @Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String,@Query("page") page : String) : Call<RankList>
    @GET("search/searchResult")
    fun searchCartoon(@Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String,@Query("q") q : String) : Call<RankList>
    @GET("comic/detail_static_new")
    fun detailChapters(@Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String,@Query("comicid") comicid : String) : Call<Chapters>
    @GET("comic/chapterNew")
    fun chapterImages(@Query("come_from") come_from: String,@Query("serialNumber") serialNumber: String,@Query("v") v :String,@Query("model") model: String,@Query("chapter_id") chapter_id:String,@Query("android_id") android_id: String) : Call<ImagesObtain>
}