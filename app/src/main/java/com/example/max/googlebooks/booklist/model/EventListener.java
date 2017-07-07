package com.example.max.googlebooks.booklist.model;

import com.example.max.googlebooks.book.Book;

import java.util.List;

/**
 * Created by max on 7/6/17.
 */

public interface EventListener {
    void booksUpdated(List<Book> books);
    void noMoreBooks(List<Book> books);
    void requestFailed();
}
