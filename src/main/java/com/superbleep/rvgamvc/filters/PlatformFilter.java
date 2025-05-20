package com.superbleep.rvgamvc.filters;

public class PlatformFilter {
    private String name;
    private String manufacturer;
    private Integer releaseYear;

    public PlatformFilter() {
    }

    public PlatformFilter(String name, String manufacturer, Integer releaseYear) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
