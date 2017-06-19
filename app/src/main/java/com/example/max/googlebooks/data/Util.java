package com.example.max.googlebooks.data;

import android.os.Build;
import android.text.Html;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by max on 6/19/17.
 */

public class Util {

    public static String formatDate(String rawDate) {
        if (rawDate == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(rawDate);
            return DateFormat.getDateInstance().format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rawDate;
    }

    public static String htmlToText(String html) {
        if (html == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString();
        return Html.fromHtml(html).toString();
    }

    public static String languageCodeToName(String code) {
        if (code == null) return null;
        Locale local = new Locale(code);
        return local.getDisplayLanguage();
    }
}
