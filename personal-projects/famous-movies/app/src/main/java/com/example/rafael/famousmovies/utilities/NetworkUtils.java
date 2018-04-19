package com.example.rafael.famousmovies.utilities;

import android.content.Context;
import android.net.Uri;

import com.example.rafael.famousmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String famousMoviesDiscoverBaseUrl = "http://api.themoviedb.org/3/movie";
    private static final String famousMoviesPosterBaseUrl = "http://image.tmdb.org/t/p/w185";
    private static final String returnFormat = "json";

    final static String FORMAT_PARAM = "mode";
    final static String TARGET_PAGE_PARAM = "page";
    final static String API_KEY = "api_key";

    public enum EnumSortBy {
        top_rated,
        popular
    }

    public static URL buildDiscoverUrl(EnumSortBy targetSortBy, Integer targetPage, Context context) {
        String baseUrl = famousMoviesDiscoverBaseUrl.concat("/").concat(targetSortBy.toString());

        String apiKey = context.getString(R.string.THE_MOVIE_DB_API_TOKEN);

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(API_KEY, apiKey)
                .appendQueryParameter(TARGET_PAGE_PARAM, targetPage.toString())
                .appendQueryParameter(FORMAT_PARAM, returnFormat)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String buildPosterUrl(String posterPath) {
        posterPath = famousMoviesPosterBaseUrl.concat(posterPath);
        return posterPath;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
