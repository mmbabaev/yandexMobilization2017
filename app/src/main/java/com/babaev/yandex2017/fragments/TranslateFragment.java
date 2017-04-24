package com.babaev.yandex2017.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText sourceEditText;
    TextView resultTextView;
    Button translateButton;

    CheckBox favoriteCheckBox;

    List<Language> languages;
    Language sourceLanguage;
    Language targetLanguage;

    Translation current;

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

        sourceSpinner.setSelection(0);
        targetSpinner.setSelection(1);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceLanguage = languages.get(position);
                resultTextView.setText("");
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

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sourceEditText.getText().length() == 0) {
                    Toast toast = Toast.makeText(getContext(), "Введите текст для перевода", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    translate();
                }
                hideKeyBoard();
            }
        });

        favoriteCheckBox = (CheckBox)view.findViewById(R.id.translate_favorite_check_box);

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                favoriteCheckBox.setVisibility(View.INVISIBLE);
                resultTextView.setText("");
            }
        });

        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceEditText.setText(resultTextView.getText());
                sourceEditText.setSelection(sourceEditText.getText().length());

                int temp = sourceSpinner.getSelectedItemPosition();
                sourceSpinner.setSelection(targetSpinner.getSelectedItemPosition());

                AdapterView.OnItemSelectedListener listener = targetSpinner.getOnItemSelectedListener();
                targetSpinner.setOnItemSelectedListener(null);
                targetSpinner.setSelection(temp);
                targetSpinner.setOnItemSelectedListener(listener);
            }
        });

        if (current == null) {
            favoriteCheckBox.setVisibility(View.INVISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
            }
        });
        return view;
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void translate() {
        String s = sourceEditText.getText().toString();
        if (sourceLanguage == null || targetLanguage == null ||
                sourceEditText.getText().toString().equals("")) {
            String as = "";
            return;
        }

        String sourceText = sourceEditText.getText().toString();

        String lang = sourceLanguage.code + "-" + targetLanguage.code;

        YandexApi.getInstance().translate(sourceText, lang, new TranslateListener() {
            @Override
            public void onResponse(final Translation translation) {
                setupWithTranslation(translation);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupWithTranslation(final Translation translation) {
        this.current = translation;

        String result = translation.getResult();
        resultTextView.setText(result);

        favoriteCheckBox.setOnCheckedChangeListener(null);
        favoriteCheckBox.setVisibility(View.VISIBLE);
        favoriteCheckBox.setChecked(translation.isFavorite());
        favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                translation.setFavorite(isChecked);
            }
        });
    }
}
