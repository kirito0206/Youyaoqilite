package com.example.youyaoqilite;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
