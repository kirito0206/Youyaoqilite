package com.example.youyaoqilite.ui.collection.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.adapter.RecyclerViewAdapter
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import kotlinx.android.synthetic.main.fragment_item_history.view.*

class ItemHistory : Fragment() {

    //private lateinit var fragBinding : FragmentItemHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragBinding = FragmentItemHistoryBinding.inflate(layoutInflater)
        val view = inflater!!.inflate(R.layout.fragment_item_history, container, false)
        var cartoonList = CartoonDaoOpe.getInstance().queryHistoryAll(MyApplication.getContext())
        val layoutManager = LinearLayoutManager(activity)
        view.history_recyclerView.layoutManager = layoutManager

        if (cartoonList != null){
            if (cartoonList.size != 0){
                view.history_image.visibility = View.GONE
                view.history_text.visibility = View.GONE
                view.history_recyclerView.adapter = RecyclerViewAdapter(cartoonList,this)
            }
        }
        return view
        //return fragBinding.root
    }
}