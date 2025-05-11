package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Emulator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String developer;

    @Temporal(TemporalType.DATE)
    private Date release;

    @ManyToMany(mappedBy = "emulators", cascade = CascadeType.ALL)
    private List<Platform> platforms;

    public Emulator(Long id, String name, String developer, Date release, List<Platform> platforms) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.release = release;
        this.platforms = platforms;
    }

    public Emulator() {
    }

    public Long getId() {
        return this.id;
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

    public List<Platform> getPlatforms() {
        return this.platforms;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
