package com.babaev.yandex2017.models;

public interface TranslateListener {
    public void onResponse(Translation translation);
    public void onError(String message);
}
