package com.superbleep.rvgamvc.dto;

import java.util.Date;
import java.util.List;

public class EmulatorDTO {
    private String name;
    private String developer;
    private Date release;
    private List<Long> platformIds;

    public EmulatorDTO(String name, String developer, Date release, List<Long> platformIds) {
        this.name = name;
        this.developer = developer;
        this.release = release;
        this.platformIds = platformIds;
    }

    public EmulatorDTO() {
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
