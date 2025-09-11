package com.assignment1.movie_app.model;

public class Movie {
    private int movieId;
    private String movieName;
    private String movieActor;

    public Movie(){

    }
    public Movie(int movieId, String movieName, String movieActor) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieActor = movieActor;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieActor() {
        return movieActor;
    }

    public void setMovieActor(String movieActor) {
        this.movieActor = movieActor;
    }
}
