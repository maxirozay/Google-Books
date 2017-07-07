package com.example.max.googlebooks;

import android.app.Application;

import com.example.max.googlebooks.config.Config;

/**
 * Created by max on 7/7/17.
 */

public class ApplicationManager extends Application {

    public static Config config;

    @Override
    public void onCreate() {
        super.onCreate();
        config = new Config(this);
    }
}
