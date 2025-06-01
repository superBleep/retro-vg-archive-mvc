package com.superbleep.rvgamvc.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class GameVersionId implements Serializable {
    private String id;
    private Long gameId;

    public GameVersionId(String id, Long gameId) {
        this.id = id;
        this.gameId = gameId;
    }

    public GameVersionId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
