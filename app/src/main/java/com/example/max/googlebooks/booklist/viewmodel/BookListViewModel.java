package com.example.max.googlebooks.booklist.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.example.max.googlebooks.booklist.model.EventListener;
import com.example.max.googlebooks.booklist.model.BookListModel;
import com.example.max.googlebooks.book.Book;

import java.util.List;

/**
 * Created by max on 7/7/17.
 */

public class BookListViewModel implements EventListener {

    public final ObservableInt progressVisibility = new ObservableInt();

    private BookListModel bookListModel;
    private EventListener dataListener;

    public BookListViewModel() {
        bookListModel = new BookListModel(this);
    }

    public List<Book> getBooks() {
        return bookListModel.getBooks();
    }

    public void searchBook(String query) {
        progressVisibility.set(View.VISIBLE);
        bookListModel.searchBook(query);
    }

    public void loadMoreBooks() {
        progressVisibility.set(View.VISIBLE);
        bookListModel.loadMoreBooks();
    }

    public void setDataListener(EventListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void booksUpdated(List<Book> books) {
        progressVisibility.set(View.GONE);
        dataListener.booksUpdated(books);
    }

    @Override
    public void noMoreBooks(List<Book> books) {
        progressVisibility.set(View.GONE);
        dataListener.noMoreBooks(books);
    }

    @Override
    public void requestFailed() {
        progressVisibility.set(View.GONE);
    }

    public void destroy() {
        bookListModel.destroy();
    }
}
