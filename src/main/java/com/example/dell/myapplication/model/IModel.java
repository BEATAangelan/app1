package com.example.dell.myapplication.model;

import com.example.dell.myapplication.utils.MCallBack;

import java.util.Map;

public interface IModel {
    void getRequest(String url, Class clazz,MCallBack mCallBack);
    void postRequest(String url, Map<String,String> map,Class clazz,MCallBack mCallBack);
}
