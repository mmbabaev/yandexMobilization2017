package com.babaev.yandex2017.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    TextView sourceEditText;
    TextView resultTextView;
    Button translateButton;

    List<Language> languages;
    Language sourceLanguage;
    Language targetLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        languages = Language.getList();
        List<String> languageTitles = new ArrayList<>();
        for (Language language : languages) {
            languageTitles.add(language.title);
        }

        swapButton = (Button)view.findViewById(R.id.swap_button);

        sourceSpinner = (Spinner)view.findViewById(R.id.source_spinner);
        targetSpinner = (Spinner)view.findViewById(R.id.target_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, languageTitles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(spinnerAdapter);
        targetSpinner.setAdapter(spinnerAdapter);

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

        sourceEditText = (EditText)view.findViewById(R.id.source_edit_text);
        resultTextView = (TextView)view.findViewById(R.id.result_text_view);
        translateButton = (Button)view.findViewById(R.id.translate_button);

        sourceSpinner.setSelection(0);
        targetSpinner.setSelection(1);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
            }
        });

        return view;
    }

    public void translate() {
        if (sourceLanguage == null || targetLanguage == null) {
            return;
        }

        String sourceText = sourceEditText.getText().toString();
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
