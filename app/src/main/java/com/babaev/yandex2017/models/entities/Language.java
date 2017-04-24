package com.babaev.yandex2017.models.entities;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Entity class for source and target languages for translate
 */
public class Language extends SugarRecord {
    public String code;
    public String title;

    public Language() {}

    public Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static List<Language> getList() {

        // TODO: load dynamicaly?
        List<Language> result = Language.listAll(Language.class);
        if (result.size() == 0) {
            Language ru = new Language("ru", "Русский");
            Language en = new Language("en", "Английский");
            Language ja = new Language("ja", "Японский");
            Language de = new Language("de", "Немецкий");
            Language fr = new Language("fr", "Французский");

            result.add(ru);
            result.add(en);
            result.add(ja);
            result.add(de);
            result.add(fr);

            for (Language lang : result) {
                lang.save();
            }
        }

        return result;
    }
}
