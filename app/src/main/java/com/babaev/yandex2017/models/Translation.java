package com.babaev.yandex2017.models;

import com.babaev.yandex2017.fragments.TranslateFragment;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Translation extends SugarRecord {

    String source;
    String text;
    String lang;

    boolean favorite;
    long time;


    public Translation(String source, String text, String lang) {
        this.lang = lang;
        this.source = source;
        this.text = text;

        this.favorite = false;
        this.time = System.currentTimeMillis();
    }

    public static Translation fromJSON(JSONObject json, String source) throws JSONException {
        String text = json.getJSONArray("text").getJSONObject(0).toString();
        String lang = json.getString("lang");

        return new Translation(source, text, lang);
    }

    public List<Translation> getFavoriteList() {
        return Translation.find(Translation.class, "favorite = true");
    }

    public List<Translation> getHistoryList() {
        return Translation.findWithQuery(Translation.class, "SELECT * FROM Translation ORDER BY time DESC");
    }

    public void clearHistory() {

    }
}
