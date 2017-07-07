package com.example.max.googlebooks.bookdetails.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.max.googlebooks.R;
import com.example.max.googlebooks.bookdetails.viewmodel.BookDetailsViewModel;
import com.example.max.googlebooks.booklist.view.BookListActivity;
import com.example.max.googlebooks.databinding.BookDetailBinding;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {

    public static final String ID = "id";
    private BookDetailsViewModel viewModel;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookDetailBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.book_detail, container, false);
        View view = binding.getRoot();
        viewModel = new BookDetailsViewModel();
        viewModel.getBookDetails(getArguments().getString(ID));
        binding.setBook(viewModel.getBook());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null || getArguments().getString(ID) == null) return;

        final BookDetailActivity activity = (BookDetailActivity) this.getActivity();
        activity.binding.setBook(viewModel.getBook());
        viewModel.getBookDetails(getArguments().getString(ID));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}
