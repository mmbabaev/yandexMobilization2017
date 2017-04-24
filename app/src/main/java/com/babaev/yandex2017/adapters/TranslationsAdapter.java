package com.babaev.yandex2017.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.babaev.yandex2017.R;
import com.babaev.yandex2017.models.entities.Translation;

import java.util.List;

abstract public class TranslationsAdapter extends ArrayAdapter<Translation> implements Filterable {
    private static class TranslationViewHolder {
        CheckBox favorite;
        TextView source;
        TextView result;
        TextView lang;
    }

    private List<Translation> translations;
    private Filter filter;

    public TranslationsAdapter(Context context, List<Translation> translations) {
        super(context, R.layout.translation_row, translations);
        this.translations = translations;
    }

    @Nullable
    @Override
    public Translation getItem(int position) {
        return translations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Translation translation = translations.get(position);
        TranslationViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.translation_row, parent, false);

            viewHolder = new TranslationViewHolder();
            viewHolder.source = (TextView)convertView.findViewById(R.id.row_source_text);
            viewHolder.result = (TextView)convertView.findViewById(R.id.row_result_text);
            viewHolder.favorite = (CheckBox)convertView.findViewById(R.id.row_favorite_check_box);
            viewHolder.lang = (TextView)convertView.findViewById(R.id.row_lang_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TranslationViewHolder)convertView.getTag();
        }

        viewHolder.favorite.setOnCheckedChangeListener(null);

        viewHolder.source.setText(translation.getSource());
        viewHolder.result.setText(translation.getResult());
        viewHolder.favorite.setChecked(translation.isFavorite());
        viewHolder.lang.setText(translation.getLang());

        viewHolder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                translation.setFavorite(isChecked);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return translations.size();
    }

    abstract protected List<Translation> getFilteredTranslations(String constraint);

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new TranslationsFilter();
        }
        return filter;
    }

    private class TranslationsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Translation> translations = getFilteredTranslations(constraint.toString());
            filterResults.values = translations;
            filterResults.count = translations.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            translations = (List<Translation>)results.values;
            notifyDataSetChanged();
        }
    }
}
