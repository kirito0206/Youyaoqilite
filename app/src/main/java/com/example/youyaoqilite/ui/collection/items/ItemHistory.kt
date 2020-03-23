package com.example.youyaoqilite.ui.collection.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.adapter.RecyclerViewAdapter
import com.example.youyaoqilite.databinding.FragmentItemHistoryBinding
import com.example.youyaoqilite.greendao.CartoonDaoOpe

class ItemHistory : Fragment() {

    private lateinit var fragBinding : FragmentItemHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragBinding = FragmentItemHistoryBinding.inflate(layoutInflater)
        var cartoonList = CartoonDaoOpe.getInstance().queryAll(MyApplication.getContext())
        val layoutManager = LinearLayoutManager(activity)
        fragBinding.historyRecyclerView.layoutManager = layoutManager
        if (cartoonList != null)
        fragBinding.historyRecyclerView.adapter = RecyclerViewAdapter(cartoonList,this)

        return fragBinding.root
    }
}