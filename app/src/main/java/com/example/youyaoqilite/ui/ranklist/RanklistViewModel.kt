package com.example.youyaoqilite.ui.ranklist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.greendao.CartoonDaoOpe

class RanklistViewModel : ViewModel() {

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()
    private val _text = MutableLiveData<String>().apply {
        value = "This is ranklist Fragment"
    }
    val text: LiveData<String> = _text

    /*object SwipeRefreshLayoutBinding {

        @JvmStatic
        @BindingAdapter("app:bind_swipeRefreshLayout_refreshing")
        fun setSwipeRefreshLayoutRefreshing(
            swipeRefreshLayout: SwipeRefreshLayout,
            newValue: Boolean
        ) {
            if (swipeRefreshLayout.isRefreshing != newValue)
                swipeRefreshLayout.isRefreshing = newValue
        }

        @JvmStatic
        @InverseBindingAdapter(
            attribute = "app:bind_swipeRefreshLayout_refreshing",
            event = "app:bind_swipeRefreshLayout_refreshingAttrChanged"
        )
        fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean =
            swipeRefreshLayout.isRefreshing

        @JvmStatic
        @BindingAdapter(
            "app:bind_swipeRefreshLayout_refreshingAttrChanged",
            requireAll = false
        )
        fun setOnRefreshListener(
            swipeRefreshLayout: SwipeRefreshLayout,
            bindingListener: InverseBindingListener?
        ) {
            if (bindingListener != null)
                swipeRefreshLayout.setOnRefreshListener {
                    bindingListener.onChange()
                }
        }
    }*/
}

