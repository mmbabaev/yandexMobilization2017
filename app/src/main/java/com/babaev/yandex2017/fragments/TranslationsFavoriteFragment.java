package com.babaev.yandex2017.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.babaev.yandex2017.adapters.TranslationsAdapter;
import com.babaev.yandex2017.models.entities.Translation;

import java.util.List;

public class TranslationsFavoriteFragment extends TranslationListFragment {
    @Override
    protected TranslationsAdapter getAdapter() {
        return new TranslationsAdapter(getContext(), Translation.getFavoriteList()) {
            @Override
            protected List<Translation> getFilteredTranslations(String constraint) {
                return Translation.getFavoriteList(constraint);
            }
        };
    }

    @Override
    protected void clearList() {
        new AlertDialog.Builder(getContext())
                .setTitle("Очистить")
                .setMessage("Очистить избранное?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Translation.clearFavorite();
                        listView.setAdapter(getAdapter());
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
