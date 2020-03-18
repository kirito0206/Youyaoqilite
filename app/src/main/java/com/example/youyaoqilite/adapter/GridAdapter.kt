package com.example.youyaoqilite.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.databinding.ItemGridLayoutBinding
import com.example.youyaoqilite.ui.DetailActivity

class GridAdapter(val context: Context, private var cartoonList: MutableList<Cartoon>?): BaseAdapter() {

    // 单元格的 View
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var gridBinding = ItemGridLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        if (convertView == null) {
            var data = cartoonList?.get(position)
            Glide.with(MyApplication.getContext()).load(data?.cover).error(R.mipmap.ic_launcher).override(1200, 400).into(gridBinding.gridImage)
            gridBinding.gridName.text = data?.name
            gridBinding.root.setOnClickListener(View.OnClickListener {
                var cartoon = cartoonList?.get(position)
                var intent = Intent()
                intent.setClass(MyApplication.getContext(), DetailActivity::class.java)
                if (cartoon != null) {
                    intent.putExtra("cartoon_name",cartoon.name)
                    intent.putExtra("cartoon_cover",cartoon.cover)
                    intent.putExtra("cartoon_tags",cartoon.tags)
                    intent.putExtra("cartoon_description",cartoon.description)
                    intent.putExtra("cartoon_id",cartoon.comicid)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    MyApplication.getContext().startActivity(intent)
                }
            })
        } else {
            return convertView
        }

        return gridBinding.root
    }

    // 要渲染的单元格数量
    override fun getCount(): Int {
        if (cartoonList == null)
            return 0
        return cartoonList!!.size
    }

    // 在这个示例中不用，Android 要求实现此方法
    override fun getItem(position: Int): Any? {
        return null
    }

    // 在这个示例中不用，Android 要求实现此方法
    override fun getItemId(position: Int): Long {
        return 0
    }
}