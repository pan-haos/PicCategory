package com.we.piccategory.ui.base;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

/**
 * Created by 86119 on 2017/3/3.
 */

public class BaseApp extends Application {
    public static String TAG = "ph";

    public static Context context;

    public static AssetManager assets;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        assets = getAssets();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
