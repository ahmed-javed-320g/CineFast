package com.example.a0644_a1;

public class movie {
    private String title;
    private String genre;
    private int posterResId;
    private String trailerUrl;
    private boolean isComingSoon;

    public movie(String title, String genre, int posterResId, String trailerUrl, boolean isComingSoon) {
        this.title = title;
        this.genre = genre;
        this.posterResId = posterResId;
        this.trailerUrl = trailerUrl;
        this.isComingSoon = isComingSoon;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getPosterResId() { return posterResId; }
    public String getTrailerUrl() { return trailerUrl; }
    public boolean isComingSoon() { return isComingSoon; }
}
