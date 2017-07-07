package com.example.max.googlebooks.book;

import java.util.List;

/**
 * Created by max on 6/15/17.
 */

public class ItemList<T> {

    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
