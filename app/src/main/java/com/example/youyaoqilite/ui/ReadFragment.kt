package com.example.youyaoqilite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.youyaoqilite.MyApplication
import com.example.youyaoqilite.R
import com.example.youyaoqilite.databinding.FragmentReadBinding

class ReadFragment(var image : String) : Fragment() {

    private lateinit var fragBinding : FragmentReadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragBinding = FragmentReadBinding.inflate(layoutInflater)
        MyApplication.statusBarHide(activity)
        Glide.with(this).load(image).error(R.mipmap.ic_launcher).into(fragBinding.readImage)

        return fragBinding.root
    }
}