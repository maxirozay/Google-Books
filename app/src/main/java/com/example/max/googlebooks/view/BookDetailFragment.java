package com.example.max.googlebooks.view;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.max.googlebooks.R;
import com.example.max.googlebooks.data.Data;
import com.example.max.googlebooks.model.Book;
import com.example.max.googlebooks.network.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {

    private NetworkManager networkManager = new NetworkManager();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Data.getSelectedBook() == null) return;

        final Activity activity = this.getActivity();

        showBasicInfo();
        if (Data.getSelectedBook().hasDetails()) {
            showMoreDetails();
        } else {
            networkManager.getBookDetails(Data.getSelectedBook().getId(),
                    new Callback<Book>() {

                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            if (response.isSuccessful()) {
                                response.body().setHasDetails(true);
                                Data.setSelectedBook(response.body());
                                showMoreDetails();
                            }
                        }

                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            if (!call.isCanceled()) {
                                Toast.makeText(activity,
                                        getString(R.string.connexion_failed),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    private void showBasicInfo() {
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) this.getActivity()
                .findViewById(R.id.toolbar_layout);
        Glide.with(this)
                .load(Data.getSelectedBook().getCoverImageLink())
                .error(R.mipmap.ic_launcher)
                .into((ImageView) appBarLayout.findViewById(R.id.collapsingImage));
        Glide.with(this)
                .load(Data.getSelectedBook().getCoverImageLink())
                .error(R.mipmap.ic_launcher)
                .into((ImageView) getView().findViewById(R.id.coverImage));
        ((TextView) getView().findViewById(R.id.title)).setText(Data.getSelectedBook()
                .getVolumeInfo().getTitle());

        String authors = "";
        for (String author : Data.getSelectedBook().getVolumeInfo().getAuthors())
            authors += author + ", ";
        if (authors.length() > 2) {
            ((TextView) getView().findViewById(R.id.author))
                    .setText(authors.substring(0, authors.length() - 2));
        } else getView().findViewById(R.id.author).setVisibility(View.GONE);

        if (Data.getSelectedBook().getVolumeInfo().getPublishedDate() != null) {
            ((TextView) getView().findViewById(R.id.publishedDate))
                    .setText(Data.getSelectedBook().getVolumeInfo().getPublishedDate());
        } else getView().findViewById(R.id.publishedDate).setVisibility(View.GONE);

        ((RatingBar) getView().findViewById(R.id.ratingBar))
                .setRating(Data.getSelectedBook().getVolumeInfo().getAverageRating());
        ((TextView) getView().findViewById(R.id.ratingsCount))
                .setText(String.valueOf(Data.getSelectedBook().getVolumeInfo().getRatingsCount()));
    }

    private void showMoreDetails() {
        ((TextView) getView().findViewById(R.id.description))
                .setText(Data.getSelectedBook().getVolumeInfo().getDescription());

        displayFieldWithLabel(Data.getSelectedBook().getAccessInfo().getWebReaderLink(),
                R.id.webReaderLink, R.id.webReaderLinkLabel);
        displayFieldWithLabel(Data.getSelectedBook().getVolumeInfo().getLanguage(),
                R.id.language, R.id.languageLabel);
        displayFieldWithLabel(Data.getSelectedBook().getVolumeInfo().getPublisher(),
                R.id.publisher, R.id.publisherLabel);
        displayFieldWithLabel(String.valueOf(Data.getSelectedBook().getVolumeInfo().getPageCount()),
                R.id.pages, R.id.pagesLabel);

        String categories = "";
        for (String category : Data.getSelectedBook().getVolumeInfo().getCategories())
            categories += category + "\n";
        if (categories.length() > 2) displayFieldWithLabel(categories,
                R.id.categories, R.id.categoriesLabel);
    }

    private void displayFieldWithLabel(String text, int viewId, int labelViewId) {
        if (text != null || text.length() == 0) {
            getView().findViewById(labelViewId).setVisibility(View.VISIBLE);
            getView().findViewById(viewId).setVisibility(View.VISIBLE);
            ((TextView) getView().findViewById(viewId)).setText(text);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        networkManager.cancelCalls();
    }
}
