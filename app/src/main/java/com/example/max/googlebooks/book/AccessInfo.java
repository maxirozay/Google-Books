package com.example.max.googlebooks.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by max on 6/19/17.
 */

public class AccessInfo extends BaseObservable {

    private String webReaderLink;

    @Bindable
    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }
}
