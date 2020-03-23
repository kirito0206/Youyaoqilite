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
import com.example.youyaoqilite.data.Chapter
import com.example.youyaoqilite.databinding.ItemChaptersLayoutBinding
import com.example.youyaoqilite.ui.ReadActivity

class ChapterAdapter(private val chapterList: MutableList<Chapter>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = ItemChaptersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var holder = ViewHolder(binding)
        holder.itemView.setOnClickListener(View.OnClickListener {
            var position = holder.adapterPosition
            var chapter = chapterList[position]
            var intent = Intent()
            intent.setClass(MyApplication.getContext(),ReadActivity::class.java)
            intent.putExtra("chapter_id",chapter.chapter_id)
            intent.putExtra("image_total",chapter.image_total)
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
}