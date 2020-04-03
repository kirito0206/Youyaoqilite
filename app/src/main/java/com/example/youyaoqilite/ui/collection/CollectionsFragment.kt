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
import com.example.youyaoqilite.R
import com.example.youyaoqilite.ui.collection.items.ItemCollection
import com.example.youyaoqilite.ui.collection.items.ItemHistory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_collection.view.*

class CollectionsFragment : Fragment() {

    //private lateinit var fragBinding: FragmentCollectionBinding
    private lateinit var itemCollection : Fragment
    private lateinit var itemHistory : Fragment

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //fragBinding = FragmentCollectionBinding.inflate(layoutInflater)
        val view = inflater!!.inflate(R.layout.fragment_collection, container, false)
        itemCollection = ItemCollection()
        itemHistory = ItemHistory()
        initViewPager(view)
        initTabViewPager(view)
        return view
        //return fragBinding.root
    }

    private fun initViewPager(view : View){
        view.view_pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                if (position == 0)
                    return itemCollection
                return itemHistory
            }
        }

        view.view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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

    private fun initTabViewPager(view : View) {
        TabLayoutMediator(view.tab_layout, view.view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0)
                    tab.text = "收藏"
                else
                    tab.text = "历史"
            }
        ).attach()
    }

}
