package com.babaev.yandex2017;


import android.app.Application;

import com.babaev.yandex2017.models.entities.Language;
import com.orm.SugarContext;

public class YMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(getApplicationContext());
        Language.findById(Language.class, (long)1);
    }
}
