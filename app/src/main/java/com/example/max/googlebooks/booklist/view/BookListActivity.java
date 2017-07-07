package com.example.max.googlebooks.booklist.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.max.googlebooks.booklist.model.EventListener;
import com.example.max.googlebooks.R;
import com.example.max.googlebooks.bookdetails.view.BookDetailActivity;
import com.example.max.googlebooks.bookdetails.view.BookDetailFragment;
import com.example.max.googlebooks.booklist.viewmodel.BookListViewModel;
import com.example.max.googlebooks.databinding.ActivityBookListBinding;
import com.example.max.googlebooks.book.Book;

import java.util.List;

/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity implements EventListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public RecyclerView.Adapter adapter;
    private BookListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new BookListViewModel();
        viewModel.setDataListener(this);
        viewModel.searchBook("book");
        ActivityBookListBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_book_list);
        binding.setProgressVisibility(viewModel.progressVisibility);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final View recyclerView = findViewById(R.id.book_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        adapter = ((RecyclerView) recyclerView).getAdapter();

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Get the query from the search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            viewModel.searchBook(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(viewModel.getBooks()));
    }

    @Override
    public void booksUpdated(List<Book> books) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noMoreBooks(List<Book> books) {
        adapter.notifyDataSetChanged();
        Toast.makeText(this,
                getString(R.string.no_more_result),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void requestFailed() {
        Toast.makeText(this,
                getString(R.string.connexion_failed),
                Toast.LENGTH_LONG).show();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Book> mValues;

        public SimpleItemRecyclerViewAdapter(List<Book> books) {
            mValues = books;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (position == mValues.size() - 10) viewModel.loadMoreBooks();

            holder.mItem = mValues.get(position);
            Glide.with(holder.mView.getContext())
                    .load(holder.mItem.getCoverThumbnailLink())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.mCoverImageView);
            holder.mTitleView.setText(mValues.get(position).getVolumeInfo().getTitle());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTwoPane) {
                        BookDetailFragment fragment = new BookDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(BookDetailFragment.ID, holder.mItem.getId());
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.book_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                        intent.putExtra(BookDetailFragment.ID, holder.mItem.getId());
                        BookListActivity.this.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mCoverImageView;
            public final TextView mTitleView;
            public Book mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mCoverImageView = (ImageView) view.findViewById(R.id.coverImage);
                mTitleView = (TextView) view.findViewById(R.id.title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}
