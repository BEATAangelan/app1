package com.example.dell.myapplication.presenter;

import com.example.dell.myapplication.utils.MCallBack;

import java.util.Map;

public interface IPersenter {
    void getRequest(String url, Class clazz);
    void postRequest(String url, Map<String,String> map, Class clazz);
}
