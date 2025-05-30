package com.superbleep.rvgamvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class EmulatorDTO {
    private Long id;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Developer is required!")
    private String developer;

    @NotNull(message = "Release date cannot be null!")
    @PastOrPresent(message = "Release date cannot be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release;

    private List<Long> platformIds;

    public EmulatorDTO(Long id, String name, String developer, Date release, List<Long> platformIds) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.release = release;
        this.platformIds = platformIds;
    }

    public EmulatorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public Date getRelease() {
        return this.release;
    }

    public List<Long> getPlatformIds() {
        return this.platformIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setPlatformIds(List<Long> platformIds) {
        this.platformIds = platformIds;
    }
}
