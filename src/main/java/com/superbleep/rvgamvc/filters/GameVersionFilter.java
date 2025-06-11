package com.superbleep.rvgamvc.filters;

public class GameVersionFilter {
    private String gameTitle;
    private String version;
    private Integer releaseYear;
    private String notes;

    public GameVersionFilter() {
    }

    public GameVersionFilter(String gameTitle, String version, Integer releaseYear, String notes) {
        this.gameTitle = gameTitle;
        this.version = version;
        this.releaseYear = releaseYear;
        this.notes = notes;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
