package com.example.youyaoqilite.adapter

import android.content.Context
import android.content.Intent
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.databinding.ItemGridLayoutBinding
import com.example.youyaoqilite.databinding.ItemSearchGridBinding
import com.example.youyaoqilite.ui.DetailActivity
import com.example.youyaoqilite.ui.search.SearchFragment

class SearchGridAdapter (val context: Context, private var textList: MutableList<String>?): BaseAdapter() {

    // 单元格的 View
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var gridBinding = ItemSearchGridBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        if (convertView == null) {
            var data = textList?.get(position)
            gridBinding.textContent.text = data
            gridBinding.root.setOnClickListener(View.OnClickListener {
                if (data != null) {
                    var message = Message()
                    message.what = 8
                    message.obj = data
                    SearchFragment.handler.mHandler.sendMessage(message)
                }
            })
        } else {
            return convertView
        }

        return gridBinding.root
    }

    // 要渲染的单元格数量
    override fun getCount(): Int {
        if (textList == null)
            return 0
        return textList!!.size
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