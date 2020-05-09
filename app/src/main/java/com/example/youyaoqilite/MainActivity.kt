package com.example.youyaoqilite

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    object handler{
        var mHandler = Handler{false}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
                 //R.id.navigation_ranklist, R.id.navigation_read, R.id.navigation_search,R.id.navigation_collection))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        initHandler()
    }

    private fun initHandler(){
        handler.mHandler = Handler{
            if (it.what == 1){
                nav_view.visibility = View.GONE
            }
            else if(it.what == 2){
                nav_view.visibility = View.VISIBLE
            }
            false
        }
    }
}
