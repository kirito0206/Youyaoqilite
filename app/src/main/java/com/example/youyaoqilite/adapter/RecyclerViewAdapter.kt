package com.example.youyaoqilite.adapter

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.databinding.FooterBinding
import com.example.youyaoqilite.databinding.HeaderBinding
import com.example.youyaoqilite.databinding.ItemCartoonLayoutBinding
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import com.example.youyaoqilite.ui.DetailActivity
import com.example.youyaoqilite.ui.ranklist.RanklistFragment
import com.example.youyaoqilite.ui.search.SearchFragment

class RecyclerViewAdapter(private var cartoonList: MutableList<Cartoon>,private var fragment: Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER : Int = 0
    private val TYPE_LIST : Int = 1
    private val TYPE_Footer : Int = 2
    private var displaySize : Int = if(fragment is SearchFragment && cartoonList.size >= 10)10 else cartoonList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER)
        {
            val headerBinding = HeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(headerBinding,fragment)
        }
        else if(viewType == TYPE_Footer)
        {
            val footerBinding = FooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FooterViewHolder(footerBinding,fragment)
        }

        var itemBinding = ItemCartoonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var holder = ViewHolder(itemBinding)
        holder.itemView.setOnClickListener(View.OnClickListener {
            var position = holder.adapterPosition
            var cartoon = cartoonList[position-1]
            var intent = Intent()
            intent.setClass(MyApplication.getContext(),DetailActivity::class.java)
            intent.putExtra("cartoon_name",cartoon.name)
            intent.putExtra("cartoon_cover",cartoon.cover)
            intent.putExtra("cartoon_tags",cartoon.tags)
            intent.putExtra("cartoon_description",cartoon.description)
            intent.putExtra("cartoon_id",cartoon.comicid)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            MyApplication.getContext().startActivity(intent)
        })
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.bind(cartoonList[position])

        when (holder) {
            is ViewHolder -> holder.bind(cartoonList[position-1])
            is FooterViewHolder -> holder.bind()
            is HeaderViewHolder -> holder.bind()
        }
    }

    class ViewHolder(private val itembinding: ItemCartoonLayoutBinding)
        : RecyclerView.ViewHolder(itembinding.root) {
        fun bind(data: Cartoon) {
            Glide.with(MyApplication.getContext()).load(data.cover).error(R.mipmap.ic_launcher).override(1200, 400).into(itembinding.cartoonCover)
            itembinding.cartoonName.text = data.name
            itembinding.cartoonTags.text = data.tags
            itembinding.cartoonDescription.text = data.description
            if (CartoonDaoOpe.getInstance().queryForName(MyApplication.getContext(),data.name)?.size  != 0){
                itembinding.cartoonCollection.text = "已收藏"
            }else{
                itembinding.cartoonCollection.text = "收藏"
            }
            itembinding.cartoonCollection.setOnClickListener {
                if (CartoonDaoOpe.getInstance().queryForName(MyApplication.getContext(),data.name)?.size != 0){
                    itembinding.cartoonCollection.text = "收藏"
                    CartoonDaoOpe.getInstance().deleteData(MyApplication.getContext(),data)
                    Log.d("123",CartoonDaoOpe.getInstance().queryAll(MyApplication.getContext())?.size.toString())
                }else{
                    itembinding.cartoonCollection.text = "已收藏"
                    CartoonDaoOpe.getInstance().insertData(MyApplication.getContext(),data)
                    Log.d("123",CartoonDaoOpe.getInstance().queryAll(MyApplication.getContext())?.size.toString())

                }
            }
        }
    }

    class HeaderViewHolder(private val binding: HeaderBinding,private var fragment: Fragment)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if(fragment is RanklistFragment)
                binding.headerText.text = "每周二上午十点刷新"
            else if(fragment is SearchFragment)
                binding.headerText.text = "已为你展示搜索内容"
        }
    }

    class FooterViewHolder(private val binding: FooterBinding,private var fragment: Fragment)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (fragment is RanklistFragment && RanklistFragment.handler.page < 8) {
                binding.footerText.text = "下拉加载更多"
                RanklistFragment.handler.page++
                if(RanklistFragment.handler.page == 1)
                    return
                var message = Message()
                message.what = 2
                RanklistFragment.handler.mHandler.sendMessage(message)
            }
            else if(fragment is SearchFragment){
                binding.footerText.text = "0_0我也是有底线的！！"
            }
            else
                binding.footerText.text = "0_0我也是有底线的！！"
        }
    }

    override fun getItemCount(): Int {
        if (cartoonList.size == 0)
            return 0
        return cartoonList.size+2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            cartoonList.size+1 -> TYPE_Footer
            else -> TYPE_LIST
        }
    }
}