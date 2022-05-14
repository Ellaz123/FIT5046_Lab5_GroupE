package com.example.fit5046_lab5_groupe;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {
   // 必须要用的Okhttpclient实例,在构造器中实例化保证单一实例
   private static OkHttpClient okHttpClient;
   //防止多个线程同时访问
   //网络访问要求singleton
   private volatile static OkHttpUtil instance;
   //把构造器定义为私有的，只有OkHttpUtil类内可以调用构造器
   private OkHttpUtil(){
      okHttpClient = new OkHttpClient();
      //请求超时设置
      okHttpClient.newBuilder()
              .connectTimeout(10, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              .writeTimeout(10, TimeUnit.SECONDS)
              .build();
   }
   /**
    * 懒汉式双重检查加锁单例模式
    */
   public static OkHttpUtil getInstance(){
      if(instance == null){
         synchronized (OkHttpUtil.class){
            if(instance == null) {
               instance = new OkHttpUtil();
            }
         }
      }
      return instance;
   }
   /**
    * get异步请求不传参数
    * 通过response.body().string()获取返回的字符串
    * 异步返回值不能更新UI，要开启新线程
    */
   public void Get(String url, Callback callback){
      final Request request = new Request.Builder()
              .url(url)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(callback);
   }
}
