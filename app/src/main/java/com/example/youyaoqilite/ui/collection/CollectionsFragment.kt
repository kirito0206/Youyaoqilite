package com.example.youyaoqilite.ui.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.adapter.GridAdapter
import com.example.youyaoqilite.greendao.CartoonDaoOpe
import com.example.youyaoqilite.ui.collection.items.ItemCollection
import com.example.youyaoqilite.ui.collection.items.ItemHistory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_collection.*
import kotlinx.android.synthetic.main.fragment_collection.view.*
import kotlinx.android.synthetic.main.fragment_collection.view.collection_edit

class CollectionsFragment : Fragment(),View.OnClickListener {

    //private lateinit var fragBinding: FragmentCollectionBinding
    private lateinit var itemCollection : Fragment
    private lateinit var itemHistory : Fragment
    private lateinit var view0 : View
    object static{
        var flag = false
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //fragBinding = FragmentCollectionBinding.inflate(layoutInflater)
        view0 = inflater.inflate(R.layout.fragment_collection, container, false)
        itemCollection = ItemCollection()
        itemHistory = ItemHistory()
        initView()
        initViewPager()
        initTabViewPager()
        return view0
        //return fragBinding.root
    }

    private fun initView(){
        view0.collection_delete.visibility = View.GONE
        view0.collection_delete.setOnClickListener(this)
        view0.collection_edit.setOnClickListener(this)
    }

    private fun initViewPager(){
        view0.view_pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                if (position == 0)
                    return itemCollection
                return itemHistory
            }
        }

        view0.view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d("123", "onPageScrollStateChanged: $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d("123", "onPageScrolled：$position $positionOffset $positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 很多情况下在页面切换完成后在此方法进行各种操作
                Log.i("123", "onPageSelected：$position")
            }
        })
        /*// 生成模拟数据
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                //refreshListFragment(listDataFragment)
                viewpager.adapter?.notifyDataSetChanged()
            }
        })*/
    }

    private fun initTabViewPager() {
        TabLayoutMediator(view0.tab_layout,view0.view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0)
                    tab.text = "收藏"
                else
                    tab.text = "历史"
            }
        ).attach()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.collection_edit -> editCollection()
                R.id.collection_delete -> deleteCollection()
            }
        }
    }

    private fun editCollection(){
        if (!static.flag){
            static.flag = true
            collection_edit.setImageResource(R.drawable.close)
            collection_delete.visibility = View.VISIBLE
            initViewPager()
        }else{
            static.flag = false
            collection_delete.visibility = View.GONE
            collection_edit.setImageResource(R.drawable.edit)
            initViewPager()
        }
    }

    private fun deleteCollection(){
        var cartoonList = CartoonDaoOpe.getInstance().queryCollectionAll(activity)
        for (i in 0 until GridAdapter.gridStatic.deleteList.size){
            var index = GridAdapter.gridStatic.deleteList[i]
            if (cartoonList != null) {
                CartoonDaoOpe.getInstance().deleteCollectedData(activity, cartoonList[index])
            }
        }
        GridAdapter.gridStatic.deleteList.clear()
        itemCollection = ItemCollection()
        initViewPager()
    }
}
