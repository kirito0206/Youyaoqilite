package com.example.youyaoqilite.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import kotlinx.android.synthetic.main.fragment_read.view.*

class ReadFragment(var image : String) : Fragment() {

    //private lateinit var fragBinding : FragmentReadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragBinding = FragmentReadBinding.inflate(layoutInflater)
        val view = inflater!!.inflate(R.layout.fragment_read, container, false)
        Glide.with(this).load(image).error(R.mipmap.ic_launcher).into(view.read_image)
        return view
    }
}