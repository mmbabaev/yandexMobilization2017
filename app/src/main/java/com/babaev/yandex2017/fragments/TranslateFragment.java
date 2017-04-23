package com.babaev.yandex2017.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.babaev.yandex2017.R;
import com.babaev.yandex2017.models.api.TranslateListener;
import com.babaev.yandex2017.models.api.YandexApi;
import com.babaev.yandex2017.models.entities.Language;
import com.babaev.yandex2017.models.entities.Translation;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Fragment} subclass for main translate logic
 */
public class TranslateFragment extends Fragment {
    Button swapButton;
    Spinner sourceSpinner;
    Spinner targetSpinner;
    TextView sourceTextView;
    TextView resultTextView;
    Button translateButton;

    List<Language> languages;
    Language sourceLanguage;
    Language targetLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languages = Language.getList();
        List<String> languageTitles = new ArrayList<>();
        for (Language language : languages) {
            languageTitles.add(language.title);
        }

        swapButton = (Button)getView().findViewById(R.id.swap_button);

        sourceSpinner = (Spinner)getView().findViewById(R.id.source_spinner);
        targetSpinner = (Spinner)getView().findViewById(R.id.target_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, languageTitles);
        sourceSpinner.setAdapter(spinnerAdapter);
        targetSpinner.setAdapter(spinnerAdapter);

        sourceSpinner.setSelection(0);
        targetSpinner.setSelection(1);
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceLanguage = languages.get(position);
                translate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetLanguage = languages.get(position);
                translate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sourceTextView = (TextView)getView().findViewById(R.id.source_text_view);
        resultTextView = (TextView)getView().findViewById(R.id.result_text_view);
        translateButton = (Button)getView().findViewById(R.id.translate_button);
    }

    public void translate() {
        String sourceText = sourceTextView.getText().toString();
        String lang = sourceLanguage.code + "-" + targetLanguage.code;

        YandexApi.getInstance().translate(this.getContext(), sourceText, lang, new TranslateListener() {
            @Override
            public void onResponse(Translation translation) {
                String result = translation.getResult();
                resultTextView.setText(result);

                //TODO: favorite logic
            }

            @Override
            public void onError(String message) {
                //TODO: show toast message
            }
        });
    }
}
