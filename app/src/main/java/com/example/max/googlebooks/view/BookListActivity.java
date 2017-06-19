package com.example.max.googlebooks.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.max.googlebooks.R;
import com.example.max.googlebooks.data.Data;
import com.example.max.googlebooks.model.Book;
import com.example.max.googlebooks.model.ItemList;
import com.example.max.googlebooks.network.NetworkManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView.Adapter adapter;
    private int startIndex = 0;
    private int maxResults = 20;
    private boolean noMoreResult = false;
    private String query = "";
    private NetworkManager networkManager = new NetworkManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

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

        searchBook("book");
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
            startIndex = 0;
            searchBook(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    // Search a book with the Google Book API
    private void searchBook(String query){
        if (startIndex > 0 && noMoreResult) return;
        else noMoreResult = false;

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        this.query = query;
        networkManager.searchBook(this, query, startIndex, maxResults, new Callback<ItemList>() {

            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (startIndex == 0) Data.getBooks().clear();
                    if (response.body().getItems() != null) {
                        try {
                            Data.getBooks().addAll(response.body().getItems());
                        } catch (OutOfMemoryError error) {
                            Data.getBooks().clear();
                            Data.getBooks().addAll(response.body().getItems());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.no_more_result),
                                Toast.LENGTH_LONG).show();
                        noMoreResult = true;
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                if (!call.isCanceled()) {
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.connexion_failed),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Data.getBooks()));
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
            if (position == mValues.size() - maxResults) {
                startIndex = mValues.size();
                searchBook(query);
            }

            holder.mItem = mValues.get(position);
            Glide.with(holder.mView.getContext())
                    .load(holder.mItem.getCoverThumbnailLink())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.mCoverImageView);
            holder.mTitleView.setText(mValues.get(position).getVolumeInfo().getTitle());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data.selectedBookPosition = position;
                    if (mTwoPane) {
                        BookDetailFragment fragment = new BookDetailFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.book_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, BookDetailActivity.class);

                        context.startActivity(intent);
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
        networkManager.cancelCalls();
    }
}
