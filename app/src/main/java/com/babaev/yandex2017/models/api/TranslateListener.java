package com.babaev.yandex2017.models.api;

import com.babaev.yandex2017.models.entities.Translation;

public interface TranslateListener {
    public void onResponse(Translation translation);
    public void onError(String message);
}
