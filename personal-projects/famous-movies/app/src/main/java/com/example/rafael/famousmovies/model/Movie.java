package com.example.rafael.famousmovies.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rafael on 12/04/2018.
 */

public class Movie {
    private Integer Id;
    private String Title;
    private String OriginalTitle;
    private String PosterPath;
    private String ReleaseDate;
    private String Overview;
    private Integer VoteCount;
    private Double VoteAverage;
    private Double Popularity;
    private JSONObject JsonObject;

    public Movie(Integer id, String title, String originalTitle, String posterPath, String releaseDate,
                 String overview, Integer voteCount, Double voteAverage, Double popularity, JSONObject jsonObject) {
        Id = id;
        Title = title;
        OriginalTitle = originalTitle;
        PosterPath = posterPath;
        ReleaseDate = releaseDate;
        Overview = overview;
        VoteCount = voteCount;
        VoteAverage = voteAverage;
        Popularity = popularity;
        JsonObject = jsonObject;
    }

    public Integer getId() { return Id; }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public Integer getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(Integer voteCount) {
        VoteCount = voteCount;
    }

    public Double getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        VoteAverage = voteAverage;
    }

    public Double getPopularity() {
        return Popularity;
    }

    public void setPopularity(Double popularity) {
        Popularity = popularity;
    }

    public JSONObject getJonObject() {
        return JsonObject;
    }

    public void setJonObject(JSONObject jsonObject) {
        JsonObject = jsonObject;
    }

    public static Movie GetMovieFromJSONObject(JSONObject jsonObject) {
        try {
            Integer id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String originalTitle = jsonObject.getString("original_title");
            String posterPath = jsonObject.getString("poster_path");
            String releaseDate = jsonObject.getString("release_date");
            String overview = jsonObject.getString("overview");
            Integer voteCount = jsonObject.getInt("vote_count");
            Double voteAverage = jsonObject.getDouble("vote_average");
            Double popularity = jsonObject.getDouble("popularity");

            Movie movie = new Movie(id, title, originalTitle, posterPath,
                    releaseDate, overview, voteCount, voteAverage, popularity, jsonObject);

            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Movie GetMovieFromJSONString(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return GetMovieFromJSONObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
