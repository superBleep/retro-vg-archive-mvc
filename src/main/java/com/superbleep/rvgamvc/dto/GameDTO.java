package com.superbleep.rvgamvc.dto;

import com.superbleep.rvgamvc.domain.Platform;
import jakarta.validation.constraints.NotBlank;

public class GameDTO {
    private Long id;

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Developer is required!")
    private String developer;

    @NotBlank(message = "Publisher is required!")
    private String publisher;

    @NotBlank(message = "Genre is required!")
    private String genre;

    private Platform platform;

    public GameDTO(Long id, String title, String developer, String publisher, String genre, Platform platform) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.genre = genre;
        this.platform = platform;
    }

    public GameDTO() {
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
}
