package com.superbleep.rvgamvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class PlatformDTO {
    private Long id;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Manufacturer is required!")
    private String manufacturer;

    @NotNull(message = "Release date cannot be null!")
    @PastOrPresent(message = "Release date cannot be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release;
    private List<Long> gameIds;
    private List<Long> emulatorIds;

    public PlatformDTO() {
    }

    public PlatformDTO(Long id, String name, String manufacturer, Date release, List<Long> gameIds, List<Long> emulatorIds) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.release = release;
        this.gameIds = gameIds;
        this.emulatorIds = emulatorIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public List<Long> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<Long> gameIds) {
        this.gameIds = gameIds;
    }

    public List<Long> getEmulatorIds() {
        return emulatorIds;
    }

    public void setEmulatorIds(List<Long> emulatorIds) {
        this.emulatorIds = emulatorIds;
    }
}
