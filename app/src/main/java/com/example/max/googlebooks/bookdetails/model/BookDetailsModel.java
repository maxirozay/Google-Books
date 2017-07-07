package com.example.max.googlebooks.bookdetails.model;

import com.example.max.googlebooks.book.Book;
import com.example.max.googlebooks.network.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by max on 7/7/17.
 */

public class BookDetailsModel {

    private Book book = new Book();
    private NetworkManager networkManager = new NetworkManager();

    public BookDetailsModel() {}

    public Book getBook() {
        return book;
    }

    public void getBookDetails(String id) {
        networkManager.getBookDetails(id,
                new Callback<Book>() {

                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        if (response.isSuccessful()) {
                            book.setVolumeInfo(response.body().getVolumeInfo());
                            book.setAccessInfo(response.body().getAccessInfo());
                            book.notifyChange();
                        }
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        if (!call.isCanceled()) {
                        }
                    }
                });
    }

    public void destroy() {
        networkManager.cancelCall();
    }
}
