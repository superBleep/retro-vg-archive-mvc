package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String manufacturer;

    @Temporal(TemporalType.DATE)
    private Date release;

    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "emulator_platform",
            joinColumns = @JoinColumn(name = "emulator_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id"))
    private List<Emulator> emulators;

    public Platform(Long id, String name, String manufacturer, Date release, List<Game> games, List<Emulator> emulators) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.release = release;
        this.games = games;
        this.emulators = emulators;
    }

    public Platform() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Date getRelease() {
        return this.release;
    }

    public List<Game> getGames() {
        return this.games;
    }

    public List<Emulator> getEmulators() {
        return this.emulators;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void setEmulators(List<Emulator> emulators) {
        this.emulators = emulators;
    }
}
