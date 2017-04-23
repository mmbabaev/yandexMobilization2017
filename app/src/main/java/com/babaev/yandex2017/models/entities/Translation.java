package com.babaev.yandex2017.models.entities;

import android.support.annotation.Nullable;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Translation extends SugarRecord {

    private String source;
    private String result;
    private String lang;

    private boolean favorite;
    private long time;

    public Translation() {}

    private Translation(String source, String result, String lang) {
        this.lang = lang;
        this.source = source;
        this.result = result;

        this.favorite = false;
        this.time = System.currentTimeMillis();
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        this.save();
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getSource() {
        return source;
    }

    public String getLang() {
        return lang.toUpperCase();
    }

    public String getResult() {
        return result;
    }

    public static Translation fromJSON(JSONObject json, String source) throws JSONException {
        String text = json.getJSONArray("text").getString(0);
        String lang = json.getString("lang");

        Translation translation = objectFromDatabase(source, text, lang);
        if (translation == null) {
            translation = new Translation(source, text, lang);
        } else {
            translation.time = System.currentTimeMillis();
        }
        translation.save();

        return translation;
    }

    public static List<Translation> getFavoriteList() {
        return Translation.find(Translation.class, "favorite = true");
    }

    public static List<Translation> getHistoryList() {
        return Translation.findWithQuery(Translation.class, "SELECT * FROM Translation ORDER BY time DESC");
    }

    public static void clearHistory() {
        Translation.deleteAll(Translation.class, "favorite = false");
    }

    public static void clearFavorite() {
        Translation.deleteAll(Translation.class, "favorite = true");
    }

    @Nullable
    private static Translation objectFromDatabase(String source, String result, String lang) {
        List<Translation> translations = Translation.find(Translation.class,
                "source = ? and result = ? and lang = ?",
                source, result, lang);

        if (translations.size() > 0) {
            return translations.get(0);
        }
        return null;
    }
}
