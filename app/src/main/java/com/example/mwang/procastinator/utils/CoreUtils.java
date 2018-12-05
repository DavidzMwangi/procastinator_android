package com.example.mwang.procastinator.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoreUtils {

    private static Retrofit retrofit = null;
    private static Retrofit auth_retrofit = null;


    public static String base_url="http://192.168.100.14/Procastinator/public/api/";

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(CoreUtils.base_url)
                    .addConverterFactory(GsonConverterFactory.create(CoreUtils.gson()))
                    .callbackExecutor(Executors.newSingleThreadExecutor());
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    NoCacheInterceptor noCacheInterceptor = new NoCacheInterceptor();
                    httpClient.addInterceptor(noCacheInterceptor);
                    builder.client(httpClient.build());
                    retrofit = builder.build();
        }
        return retrofit;
    }

    public static Gson gson(){

        return  new GsonBuilder()
                .serializeNulls()
                .create();
    }

    public static Retrofit getAuthRetrofitClient(String token) {
        if (auth_retrofit == null) {

            Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(CoreUtils.base_url)
                            .addConverterFactory(GsonConverterFactory.create(CoreUtils.gson()))
                            .callbackExecutor(Executors.newSingleThreadExecutor());
                             //build http interceptor with tokens
                            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(token);
                            NoCacheInterceptor noCacheInterceptor = new NoCacheInterceptor();
                            httpClient.addInterceptor(interceptor);
                            httpClient.addInterceptor(noCacheInterceptor);
                            builder.client(httpClient.build());

            auth_retrofit=builder.build();
        }
        return auth_retrofit;
    }

    public static int todayAsInt(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayAsString(int day) {
        switch (day){
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Day";
        }
    }
}
