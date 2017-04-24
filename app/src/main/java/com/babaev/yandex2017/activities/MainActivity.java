package com.babaev.yandex2017.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.babaev.yandex2017.R;
import com.babaev.yandex2017.fragments.TranslateFragment;
import com.babaev.yandex2017.fragments.TranslationsFavoriteFragment;
import com.babaev.yandex2017.fragments.TranslationsHistoryFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TranslateFragment translateFragment = new TranslateFragment();
        final TranslationsHistoryFragment historyFragment = new TranslationsHistoryFragment();
        final TranslationsFavoriteFragment favoriteFragment = new TranslationsFavoriteFragment();

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.translate_item:
                        selectedFragment = translateFragment;
                        break;
                    case R.id.history_item:
                        selectedFragment = historyFragment;
                        break;
                    case R.id.favorites_item:
                        selectedFragment = favoriteFragment;
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, translateFragment);
        transaction.commit();
    }


}
