package com.cuhk.MovieHeaven.entity;

import java.time.Year;
import java.util.Date;

public class Movie {
    private int movieId;

    private int userId;

    private String title;

    private String genre;

    private Year year;

    private String info;

    private float rating;

    private Date postTime;


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", info='" + info + '\'' +
                ", rating=" + rating +
                ", postTime=" + postTime +
                '}';
    }
}
