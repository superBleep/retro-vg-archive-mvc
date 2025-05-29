package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String developer;
    private String publisher;
    private String genre;

    @ManyToOne
    private Platform platform;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameVersion> gameVersions;

    public Game(Long id, String title, String developer, String publisher, String genre, Platform platform, List<GameVersion> gameVersions) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.genre = genre;
        this.platform = platform;
        this.gameVersions = gameVersions;
    }

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public List<GameVersion> getGameVersions() {
        return gameVersions;
    }

    public void setGameVersions(List<GameVersion> gameVersions) {
        this.gameVersions = gameVersions;
    }
}
