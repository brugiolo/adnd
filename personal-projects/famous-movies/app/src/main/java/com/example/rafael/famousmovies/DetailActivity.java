package com.example.rafael.famousmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rafael.famousmovies.model.Movie;
import com.example.rafael.famousmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mOriginalTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mPopularity;
    private ImageView mPoster;
    private DisplayMetrics mMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = findViewById(R.id.tv_detail_title);
        mOriginalTitle = findViewById(R.id.tv_detail_original_title);
        mOverview = findViewById(R.id.tv_detail_overview);
        mReleaseDate = findViewById(R.id.tv_detail_release_date);
        mVoteAverage = findViewById(R.id.tv_detail_vote_average);
        mPoster = findViewById(R.id.iv_detail_poster);
        mPopularity = findViewById(R.id.tv_detail_popularity);
        mMetrics = this.getResources().getDisplayMetrics();

        LoadMovieDetails();
    }

    private void LoadMovieDetails() {
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String jsonString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                Movie movie = Movie.GetMovieFromJSONString(jsonString);

                Date releaseDate =  Date.valueOf(movie.getReleaseDate());
                Locale local = new Locale("pt","BR");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", local);

                mTitle.setText(movie.getTitle());
                mOriginalTitle.setText(movie.getOriginalTitle());
                mOverview.setText(movie.getOverview());
                mReleaseDate.setText(dateFormat.format(releaseDate));
                mVoteAverage.setText(String.format("%6.2f", movie.getVoteAverage()));
                mPopularity.setText(String.format("%6.2f", movie.getPopularity()));

                String posterUrl = NetworkUtils.buildPosterUrl(movie.getPosterPath());

                Picasso.with(this)
                        .load(posterUrl)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                Integer parentWidth = mMetrics.widthPixels;
                                Integer posterWidth = bitmap.getWidth();
                                Integer posterHeight =  bitmap.getHeight();

                                Integer multiplicationFactor = (parentWidth / 2) / posterWidth;

                                mPoster.getLayoutParams().height = posterHeight * multiplicationFactor;
                                mPoster.getLayoutParams().width = posterWidth * multiplicationFactor;

                                mPoster.setImageBitmap(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
            }
        }
    }
}
