package com.babaev.yandex2017.models;

public class TranslationManager {
    public static TranslationManager INSTANCE = new TranslationManager();
}
//    private YTranslateApi api;
//    private String key = "trnsl.1.1.20170422T153930Z.0a1133b3c8394203.c920ec559b09de381a1531aab73440fecf77a2b1";
//
//    private TranslationManager() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/") //Базовая часть адреса
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        api = retrofit.create(YTranslateApi.class);
//    }
//
//    public void translate(String text, String )
//}
