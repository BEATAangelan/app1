package com.example.dell.myapplication.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private final String BASE_URL="http://172.17.8.100/small/";
    private BaseApis baseApis;
    public static synchronized RetrofitManager getInstance(){
        if(retrofitManager==null){
            retrofitManager=new RetrofitManager();
        }
        return retrofitManager;
    }
    private RetrofitManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15,TimeUnit.SECONDS);
        builder.readTimeout(15,TimeUnit.SECONDS);
        builder.writeTimeout(15,TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
         baseApis = retrofit.create(BaseApis.class);
    }
    //get请求
    public RetrofitManager get(String url,HttpListener listener){
        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return retrofitManager;

    }
    //post请求
    public RetrofitManager post(String url, Map<String,String> map, HttpListener listener){
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return retrofitManager;
    }
    private Observer getObserver(final HttpListener listener){
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if(listener!=null){
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if(listener!=null){
                        listener.onSuccess(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }


  /*  private HttpListener mListener;
    public void setHttpLinstener(HttpListener httpLinstener) {
        this.mListener = httpLinstener;
    }*/


    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String error);
    }
}
