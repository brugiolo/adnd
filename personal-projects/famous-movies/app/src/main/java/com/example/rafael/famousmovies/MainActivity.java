package com.example.rafael.famousmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rafael.famousmovies.model.Movie;
import com.example.rafael.famousmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static NetworkUtils.EnumSortBy currentSortBy;
    private static Movie[] movies;

    private Integer mCurrentPage;

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private MovieAdapter mMovieAdapter;

    public interface OnScrollListener {
        void onBottomReached(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer orientation = this.getResources().getConfiguration().orientation;
        Integer spanCount = orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;

        mMovieAdapter = new MovieAdapter(this);
        mMovieAdapter.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onBottomReached(int position) {
                mCurrentPage++;
                loadMoviesData();
            }
        });

        mRecyclerView = findViewById(R.id.rv_famous_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadMoviesData();
    }

    private void loadMoviesData() {
        showMoviesView();
        new FetchMoviesTask().execute();
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

            currentSortBy = currentSortBy == null ? NetworkUtils.EnumSortBy.top_rated : currentSortBy;
            if (mCurrentPage == null){
                mCurrentPage = 1;
                movies = null;
            }
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            try {
                if (movies == null) {
                    movies = GetMovies(currentSortBy, mCurrentPage);
                } else {
                    Movie[] newMovies = GetMovies(currentSortBy, mCurrentPage);

                    List<Movie> allMovies = new ArrayList<>(Arrays.asList(movies));
                    allMovies.addAll(Arrays.asList(newMovies));

                    movies = new Movie[allMovies.size()];
                    movies = allMovies.toArray(movies);
                }

                return movies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] moviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                showMoviesView();
                mMovieAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
    }

    private Movie[] GetMovies(NetworkUtils.EnumSortBy sortedBy, Integer targetPage) {
        try {
            URL url = NetworkUtils.buildDiscoverUrl(sortedBy, targetPage, this);
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            JSONObject jsonObjectResult = new JSONObject(result);
            JSONArray jsonArrayResults = jsonObjectResult.getJSONArray("results");
            Integer moviesLength = jsonArrayResults.length();
            Movie[] movies = new Movie[moviesLength];

            for (Integer i = 0; i < moviesLength; i++) {
                JSONObject jsonObject = jsonArrayResults.getJSONObject(i);
                Movie movie = Movie.GetMovieFromJSONObject(jsonObject);
                movies[i] = movie;
            }

            return movies;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(Movie movieSelected) throws JSONException {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        String jsonString = movieSelected.getJonObject().toString();
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, jsonString);

        startActivity(intentToStartDetailActivity);
    }

    private void showMoviesView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();

        switch (id) {
            case R.id.action_order_top_rated:
                movies = null;
                currentSortBy = NetworkUtils.EnumSortBy.top_rated;
                mCurrentPage = 1;
                loadMoviesData();
                return true;

            case R.id.action_order_popular:
                movies = null;
                currentSortBy = NetworkUtils.EnumSortBy.popular;
                mCurrentPage = 1;
                loadMoviesData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
