package com.example.max.googlebooks.network;


import com.example.max.googlebooks.ApplicationManager;
import com.example.max.googlebooks.config.Config;
import com.example.max.googlebooks.book.Book;
import com.example.max.googlebooks.book.ItemList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by max on 6/15/17.
 */

public class NetworkManager {

    private Call call;
    private static GoogleBooksAPI googleBooksAPI;

    public NetworkManager() {
        if (googleBooksAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.googleBooksAPI())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            googleBooksAPI = retrofit.create(GoogleBooksAPI.class);
        }
    }

    public void searchBook(String query,
                           int startIndex,
                           int maxResults,
                           Callback callback) {
        Call<ItemList<Book>> call = googleBooksAPI.searchVolume(
                query,
                startIndex,
                maxResults,
                ApplicationManager.config.getApiKey(),
                "items(id,volumeInfo(title,authors,publishedDate,averageRating,ratingsCount,"
                        + "imageLinks/smallThumbnail))");
        call.enqueue(callback);
        this.call = call;
    }

    public void getBookDetails(String id, Callback callback) {
        Call<Book> call = googleBooksAPI.getVolumeDetails(id);
        call.enqueue(callback);
        this.call = call;
    }

    public void cancelCall() {
        if (call != null) call.cancel();
    }
}
