package com.babaev.yandex2017.fragments;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.babaev.yandex2017.R;
import com.babaev.yandex2017.adapters.TranslationsAdapter;

public abstract class TranslationListFragment extends Fragment {

    protected ListView listView;
    protected EditText searchEditText;
    protected Button deleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translation_list, container, false);

        listView = (ListView)view.findViewById(R.id.translations_list_view);

        final TranslationsAdapter adapter = getAdapter();
        listView.setAdapter(adapter);

        searchEditText = (EditText)view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    adapter.notifyDataSetChanged();
                    return;
                }

                // dirty hack to handle enter button on simulator
                if (s.charAt(s.length() - 1) == '\n') {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    CharSequence sequence = s.subSequence(0, s.length() - 1);
                    searchEditText.setText(sequence);
                    searchEditText.setSelection(sequence.length());
                    adapter.getFilter().filter(sequence);
                    return;
                }

                if (s.length() == 0) {
                   adapter.notifyDataSetChanged();
                } else {
                    adapter.getFilter().filter(s);
                }
            }
        });

        deleteButton = (Button)view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });

        adapter.notifyDataSetChanged();

        return view;
    }

    abstract protected TranslationsAdapter getAdapter();
    abstract protected void clearList();
}