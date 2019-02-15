package com.example.dell.myapplication.model;

import com.example.dell.myapplication.utils.MCallBack;
import com.example.dell.myapplication.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelmpl implements IModel {
    MCallBack callBack;
    @Override
    public void getRequest(String url, final Class clazz, MCallBack mCallBack) {
      callBack=mCallBack;
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if(callBack !=  null){
                    callBack.setData(o);
                }

            }

            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }

    @Override
    public void postRequest(String url, Map<String, String> map, final Class clazz, MCallBack mCallBack) {
      callBack=mCallBack;
      RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
          @Override
          public void onSuccess(String data) {
              Gson gson = new Gson();
              Object o = gson.fromJson(data, clazz);
              callBack.setData(o);
          }

          @Override
          public void onFail(String error) {
             callBack.setData(error);
          }
      });
    }
}
