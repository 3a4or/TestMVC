package com.ashour.test.data.network;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "http://www.ward-ksa.com/";
    public static Retrofit retrofit = null;
    public static OkHttpClient okHttpClient = null;

    public static Api webService(){
        if (retrofit == null){
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3600, TimeUnit.SECONDS)
                    .readTimeout(3600, TimeUnit.SECONDS)
                    .writeTimeout(3600, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(Api.class);
    }

    public static void cancelAllRequests() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }

    public static void resetRetrofit() {
        retrofit = null;
    }

    public static okhttp3.Call retryLastRequest() {
        List<Call> list = null;
        Call call = null;
        if (okHttpClient != null) {
            list = okHttpClient.dispatcher().queuedCalls();
            if (list.size() != 0) {
                call = list.get(list.size() - 1);
            }
        }
        return call;
    }
}
