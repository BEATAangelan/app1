package com.example.dell.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {
    public static boolean isNetWork(Context context){
      boolean flag=false;
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       if(activeNetworkInfo!=null){
           flag = connectivityManager.getActiveNetworkInfo().isAvailable();
       }
       return flag;
    }
}
