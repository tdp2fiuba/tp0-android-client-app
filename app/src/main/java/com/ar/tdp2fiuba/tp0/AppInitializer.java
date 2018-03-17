package com.ar.tdp2fiuba.tp0;

import android.app.Application;

import com.ar.tdp2fiuba.tp0.service.network.HttpRequestHelper;

public class AppInitializer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpRequestHelper.initialize(getApplicationContext());
    }
}
