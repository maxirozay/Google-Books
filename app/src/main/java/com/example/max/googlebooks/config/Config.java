package com.example.max.googlebooks.config;

import android.content.Context;

import com.example.max.googlebooks.R;

/**
 * Created by max on 6/16/17.
 */

public class Config {

    private static Context context;
    public static String googleBooksAPI() {
        return "https://www.googleapis.com/books/v1/";
    }

    public Config(Context context) {
        this.context = context;
    }

    public String getApiKey() {
        return context.getString(R.string.apiKey);
    }
}
