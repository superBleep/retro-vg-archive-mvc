package com.superbleep.rvgamvc.filters;

public class GameFilter {
    private String title;
    private String developer;
    private String publisher;
    private String genre;
    private String platformName;

    public GameFilter() {
    }

    public GameFilter(String title, String developer, String publisher, String genre, String platformName) {
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.genre = genre;
        this.platformName = platformName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}
