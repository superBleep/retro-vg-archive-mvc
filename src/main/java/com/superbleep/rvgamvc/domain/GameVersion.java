package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
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
}
