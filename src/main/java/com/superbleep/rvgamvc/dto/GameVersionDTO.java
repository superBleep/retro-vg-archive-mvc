package com.superbleep.rvgamvc.dto;

import com.superbleep.rvgamvc.domain.GameVersionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GameVersionDTO {
    private GameVersionId id;

    @NotNull(message = "Id is required!")
    private String version;

    @NotNull(message = "Release date cannot be null!")
    @PastOrPresent(message = "Release date cannot be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release;

    @NotBlank(message = "Release notes are required!")
    private String notes;

    @NotNull(message = "Game is required!")
    private Long gameId;

    public GameVersionDTO() {
    }

    public GameVersionDTO(GameVersionId id, String version, Date release, String notes, Long gameId) {
        this.id = id;
        this.version = version;
        this.release = release;
        this.notes = notes;
        this.gameId = gameId;
    }

    public GameVersionId getId() {
        return id;
    }

    public void setId(GameVersionId id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
