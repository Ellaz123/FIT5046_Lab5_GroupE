package com.example.fit5046_lab5_groupe;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {

   private static OkHttpClient okHttpClient;
   private volatile static OkHttpUtil instance;
   private OkHttpUtil(){
      okHttpClient = new OkHttpClient();
      okHttpClient.newBuilder()
              .connectTimeout(10, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              .writeTimeout(10, TimeUnit.SECONDS)
              .build();
   }

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

   public void Get(String url, Callback callback){
      final Request request = new Request.Builder()
              .url(url)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(callback);
   }
}
