package com.superbleep.rvgamvc.filters;

public class EmulatorFilter {
    private String name;
    private String developer;
    private String platformName;
    private Integer releaseYear;

    public EmulatorFilter() {
    }

    public EmulatorFilter(String name, String developer, String platformName, Integer releaseYear) {
        this.name = name;
        this.developer = developer;
        this.platformName = platformName;
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
