package com.example.max.googlebooks.network;


import android.app.ListActivity;
import android.content.Context;

import com.example.max.googlebooks.config.Config;
import com.example.max.googlebooks.model.Book;
import com.example.max.googlebooks.model.ItemList;
import com.example.max.googlebooks.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by max on 6/15/17.
 */

public class NetworkManager {

    private List<Call> calls = new ArrayList<>();

    private GoogleBooksAPI buildGoogleBooksAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.googleBooksAPI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GoogleBooksAPI.class);

    }

    public void searchBook(Context context, String query, int startIndex, int maxResults,
                                  Callback callback) {
        GoogleBooksAPI googleBooksAPI = buildGoogleBooksAPI();
        Call<ItemList<Book>> call = googleBooksAPI.searchVolume(
                query,
                startIndex,
                maxResults,
                context.getString(R.string.apiKey),
                "items(id,volumeInfo(title,authors,publishedDate,averageRating,ratingsCount," +
                        "imageLinks/smallThumbnail))");
        call.enqueue(callback);
        calls.add(call);
    }

    public void getBookDetails(String id, Callback callback) {
        GoogleBooksAPI googleBooksAPI = buildGoogleBooksAPI();
        Call<Book> call = googleBooksAPI.getVolumeDetails(id);
        call.enqueue(callback);
        calls.add(call);
    }

    public void cancelCalls() {
        for (Call call : calls) call.cancel();
    }
}
