package com.example.max.googlebooks.booklist.model;

import com.example.max.googlebooks.book.Book;
import com.example.max.googlebooks.book.ItemList;
import com.example.max.googlebooks.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by max on 7/7/17.
 */

public class BookListModel {

    private List<Book> books = new ArrayList<>();
    private int startIndex = 0;
    private boolean noMoreResult = false;
    private String query = "";
    private NetworkManager networkManager = new NetworkManager();
    private EventListener dataListener;

    public BookListModel(EventListener dataListener) {
        this.dataListener = dataListener;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Search a book with the Google Book API
    private void requestBook(){
        int maxResults = 20;
        networkManager.searchBook(query, startIndex, maxResults, new Callback<ItemList>() {

            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (response.isSuccessful()) {
                    if (startIndex == 0) books.clear();
                    if (response.body().getItems() != null) {
                        try {
                            books.addAll(response.body().getItems());
                        } catch (OutOfMemoryError error) {
                            books.clear();
                            books.addAll(response.body().getItems());
                        }
                        if (dataListener != null) dataListener.booksUpdated(books);
                    } else {
                        noMoreResult = true;
                        if (dataListener != null) dataListener.noMoreBooks(books);
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (dataListener != null) dataListener.requestFailed();
                }
            }
        });
    }

    public void searchBook(String query) {
        startIndex = 0;
        this.query = query;
        noMoreResult = false;
        requestBook();
    }

    public void loadMoreBooks() {
        if (noMoreResult) return;
        startIndex = books.size();
        requestBook();
    }

    public void destroy() {
        networkManager.cancelCall();
    }
}
