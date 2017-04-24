package com.babaev.yandex2017.models.api;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.babaev.yandex2017.models.entities.Translation;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for working with Yandex Translator API
 */
public class YandexApi {
    private static YandexApi instance = new YandexApi();

    private String baseUrl = "https://translate.yandex.net/api/v1.5/tr.json/";
    private String key = "trnsl.1.1.20170422T153930Z.0a1133b3c8394203.c920ec559b09de381a1531aab73440fecf77a2b1";
    private Context context;

    public void translate(final String sourceText, String lang, final TranslateListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("lang", lang);
        params.put("text", sourceText);
        String url = makeUrl("translate", params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("code") == 200) {
                                Translation translation = Translation.fromJSON(response, sourceText);
                                listener.onResponse(translation);
                            } else {
                                listener.onError("Неизвестная ошибка");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Неизвестная ошибка");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError("Нет соединения с интернетом");
                    }
                });


        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private String makeUrl(String method, Map<String, String> params) {
        try {
            String result = baseUrl + method  + "?key=" + key + "&";
            for (String paramKey : params.keySet()) {
                result += paramKey;
                result += "=";
                result += URLEncoder.encode(params.get(paramKey), "UTF-8");
                result += "&";
            }

            return result.substring(0, result.length() - 1);

        } catch (Exception ex) {
            return "";
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static YandexApi getInstance() {
        return instance;
    }
}
