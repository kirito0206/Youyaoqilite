package com.example.youyaoqilite.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.data.Chapter
import com.example.youyaoqilite.databinding.ItemChaptersLayoutBinding
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import com.example.youyaoqilite.ui.ReadActivity

class ChapterAdapter(private val chapterList: MutableList<Chapter>, val context: Context,val cartoon: Cartoon) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = ItemChaptersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var holder = ViewHolder(binding)
        holder.itemView.setOnClickListener(View.OnClickListener {
            var position = holder.adapterPosition
            var chapter = chapterList[position]
            cartoon.chapterid = chapter.chapter_id
            CartoonDaoOpe.getInstance().insertHistoryData(context,cartoon)
            var intent = Intent()
            intent.setClass(MyApplication.getContext(),ReadActivity::class.java)
            intent.putExtra("chapter_id",chapter.chapter_id)
            intent.putStringArrayListExtra("chapter_id_list",chapterToString(chapterList as ArrayList<Chapter>))
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            MyApplication.getContext().startActivity(intent)
        })
        return holder
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
        holder.bind(chapterList[position])
    }

    class ViewHolder(private val binding: ItemChaptersLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Chapter) {
            Glide.with(MyApplication.getContext()).load(data.smallPlaceCover).error(R.mipmap.ic_launcher).override(250, 167).into(binding.chapterCover)
            binding.chapterName.text = data.name
        }
    }

    private fun chapterToString(chapterList: ArrayList<Chapter>) : ArrayList<String>{
        var chapterIdList = ArrayList<String>()
        for (chapter in chapterList){
            chapterIdList.add(chapter.chapter_id)
        }
        return chapterIdList
    }
}