package com.babaev.yandex2017;

import android.app.Application;

import com.babaev.yandex2017.models.api.YandexApi;
import com.orm.SugarContext;

public class YMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // setup contex for singleton classes
        SugarContext.init(getApplicationContext());
        YandexApi.getInstance().setContext(getApplicationContext());
    }
}
