package com.example.dell.myapplication.presenter;

import android.view.View;

import com.example.dell.myapplication.model.IModelmpl;
import com.example.dell.myapplication.utils.MCallBack;
import com.example.dell.myapplication.view.IView;

import java.util.Map;

public class IPersentermpl implements IPersenter {
    IModelmpl modelmpl;
    IView mview;
    public IPersentermpl(IView iView){
        modelmpl=new IModelmpl();
        mview=iView;
    }
    @Override
    public void getRequest(String url, Class clazz) {
      modelmpl.getRequest(url, clazz, new MCallBack() {
          @Override
          public void setData(Object data) {
              mview.onSuccess(data);
          }
      });
    }

    @Override
    public void postRequest(String url, Map<String, String> map, Class clazz) {
      modelmpl.postRequest(url, map, clazz, new MCallBack() {
          @Override
          public void setData(Object data) {
              mview.onSuccess(data);
          }
      });
    }
}
