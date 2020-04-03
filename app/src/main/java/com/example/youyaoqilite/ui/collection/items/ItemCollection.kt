package com.example.youyaoqilite.ui.collection.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.adapter.GridAdapter
import com.example.youyaoqilite.data.DaoMaster
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import kotlinx.android.synthetic.main.fragment_item_collection.view.*

class ItemCollection : Fragment() {

    //private lateinit var fragBinding : FragmentItemCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragBinding = FragmentItemCollectionBinding.inflate(layoutInflater)
        val view = inflater!!.inflate(R.layout.fragment_item_collection, container, false)
        var cartoonList = CartoonDaoOpe.getInstance().queryCollectionAll(MyApplication.getContext())
        if (cartoonList != null) {
            if (cartoonList.size != 0){
                view.collection_image.visibility = View.GONE
                view.collection_text.visibility = View.GONE
                view.grid_view.adapter = GridAdapter(MyApplication.getContext(),cartoonList)
            }
        }
        return view
        //return fragBinding.root
    }
}