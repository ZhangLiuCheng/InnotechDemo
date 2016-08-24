package com.innotech.demo.data.retrofit;

import android.content.Context;
import android.net.Uri;

import com.innotech.demo.utils.LogUtil;
import com.innotech.demo.utils.ToastUtil;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunyuqin on 16/8/22.
 */

public class RetrofitManager {

    private static final String BASE_URL = "http://www.blog.zhang.com";
    private static final int DEFAULT_TIMEOUT = 5;
    private static RetrofitManager sInstance = new RetrofitManager();

    private Retrofit retrofit;

    public static Retrofit getRetrofit() {
        return sInstance.retrofit;
    }

    //添加线程管理并订阅
    public static<T> void toSubscribe(Observable o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .map(new HttpResultFunc<T>())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new MyInterceptor());
        return builder.build();
    }

    // 统一处理请求信息
    private static class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("apiKey", "key");

            if(original.body() instanceof FormBody){
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) original.body();
                for (int i = 0;i < oidFormBody.size();i++){
                    newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                }
                newFormBody.add("apiToken","token");
                requestBuilder.method(original.method(), newFormBody.build());
            }
//            HttpUrl url = original.url();
//            Set<String> ps = url.queryParameterNames();
            return chain.proceed(requestBuilder.build());
        }
    }
}
