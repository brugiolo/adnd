package com.example.rafael.famousmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.rafael.famousmovies.model.Movie;
import com.example.rafael.famousmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 12/04/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private final MovieAdapterOnClickHandler mClickHandler;

    private Movie[] mMoviesData;

    MainActivity.OnScrollListener onScrollListener;
    public void setOnScrollListener(MainActivity.OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movieSelected) throws JSONException;
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mPosterImageView;
        public final Context mContext;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mPosterImageView = view.findViewById(R.id.iv_poster);
            mContext = view.getContext();
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                Integer adapterPosition = getAdapterPosition();
                Movie movieSelected = mMoviesData[adapterPosition];
                mClickHandler.onClick(movieSelected);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movieSelected = mMoviesData[position];

        String posterUrl = NetworkUtils.buildPosterUrl(movieSelected.getPosterPath());

        Picasso.with(movieAdapterViewHolder.mContext)
                .load(posterUrl).into(movieAdapterViewHolder.mPosterImageView);

        if (position == mMoviesData.length - 1){
            onScrollListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.length;
    }

    public void setMoviesData(Movie[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }
}
