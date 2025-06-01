package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
public class GameVersion {
    @EmbeddedId
    private GameVersionId id;

    @ManyToOne
    @MapsId("gameId")
    private Game game;

    @Temporal(TemporalType.DATE)
    private Date release;

    private String notes;

    public GameVersion() {
    }

    public GameVersion(GameVersionId id, Game game, Date release, String notes) {
        this.id = id;
        this.game = game;
        this.release = release;
        this.notes = notes;
    }

    public GameVersionId getId() {
        return id;
    }

    public void setId(GameVersionId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
}
