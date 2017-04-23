package com.babaev.yandex2017.models.entities;

import com.orm.SugarRecord;

import java.util.List;

public class Language extends SugarRecord {
    public String code;
    public String title;

    public Language() {}

    public Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static List<Language> getList() {
        List<Language> result = Language.listAll(Language.class);
        if (result.size() == 0) {
            Language ru = new Language("ru", "Русский");
            Language en = new Language("en", "Английский");
            Language ja = new Language("ja", "Япония");

            result.add(ru);
            result.add(en);
            result.add(ja);

            ru.save();
            en.save();
            ja.save();
        }

        return result;
    }
}
