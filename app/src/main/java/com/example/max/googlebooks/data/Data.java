package com.example.max.googlebooks.data;

import com.example.max.googlebooks.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 6/15/17.
 */

public class Data {

    public static int selectedBookPosition = 0;

    public static Book getSelectedBook() {
        if (selectedBookPosition < books.size()) return books.get(selectedBookPosition);
        else return null;
    }

    public static void setSelectedBook(Book book) {
        if (selectedBookPosition < books.size())  books.set(selectedBookPosition, book);
    }

    private static List<Book> books = new ArrayList<>();

    public static List<Book> getBooks() {
        return books;
    }

    public static void setBooks(List<Book> books) {
        Data.books = books;
    }
}
