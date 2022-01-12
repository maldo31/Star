package com.example.star;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class AppMode extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
    }
}
