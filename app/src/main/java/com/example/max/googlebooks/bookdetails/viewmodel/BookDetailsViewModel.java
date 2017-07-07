package com.example.max.googlebooks.bookdetails.viewmodel;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.max.googlebooks.book.Book;
import com.example.max.googlebooks.bookdetails.model.BookDetailsModel;

/**
 * Created by max on 7/7/17.
 */

public class BookDetailsViewModel {

    private BookDetailsModel bookDetailsModel;

    public BookDetailsViewModel() {
        bookDetailsModel = new BookDetailsModel();
    }

    public void getBookDetails(String id) {
        bookDetailsModel.getBookDetails(id);
    }

    public Book getBook() {
        return bookDetailsModel.getBook();
    }

    public void destroy() {
        bookDetailsModel.destroy();
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Glide.with(view.getContext())
                .load(url)
                .error(error)
                .into(view);
    }

}
