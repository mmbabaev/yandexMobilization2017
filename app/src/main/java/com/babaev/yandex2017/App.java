package com.babaev.yandex2017;

import android.app.Application;

import com.babaev.yandex2017.models.YTranslateApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class App extends Application {
    private static YTranslateApi api;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YTranslateApi.class);
    }

    public static YTranslateApi getApi() {
        return api;
    }
}
