package com.example.youyaoqilite.ui.collection.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.adapter.GridAdapter
import com.example.youyaoqilite.data.DaoMaster
import com.example.youyaoqilite.databinding.FragmentItemCollectionBinding
import com.example.youyaoqilite.greendao.CartoonDaoOpe

class ItemCollection : Fragment() {

    private lateinit var fragBinding : FragmentItemCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragBinding = FragmentItemCollectionBinding.inflate(layoutInflater)
        var cartoonList = CartoonDaoOpe.getInstance().queryCollectionAll(MyApplication.getContext())
        if (cartoonList != null) {
            if (cartoonList.size != 0){
                fragBinding.collectionImage.visibility = View.GONE
                fragBinding.collectionText.visibility = View.GONE
                fragBinding.gridView.adapter = GridAdapter(MyApplication.getContext(),cartoonList)
            }
        }

        return fragBinding.root
    }
}