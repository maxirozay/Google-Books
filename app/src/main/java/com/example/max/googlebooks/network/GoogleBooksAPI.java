package com.example.max.googlebooks.network;

import com.example.max.googlebooks.book.Book;
import com.example.max.googlebooks.book.ItemList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by max on 6/15/17.
 */

public interface GoogleBooksAPI {

    @Headers("User-Agent: Google Books (gzip)")
    @GET("volumes")
    Call<ItemList<Book>> searchVolume(@Query("q") String sort,
                                      @Query("startIndex") int startIndex,
                                      @Query("maxResults") int maxResults,
                                      @Query("key") String key,
                                      @Query("fields") String fields);

    @Headers("User-Agent: Google Books (gzip)")
    @GET("volumes/{id}")
    Call<Book> getVolumeDetails(@Path("id") String id);
}
