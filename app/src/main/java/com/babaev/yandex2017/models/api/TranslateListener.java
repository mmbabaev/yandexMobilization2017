package com.babaev.yandex2017.models.api;

import com.babaev.yandex2017.models.entities.Translation;

public interface TranslateListener {
    void onResponse(Translation translation);
    void onError(String message);
}
