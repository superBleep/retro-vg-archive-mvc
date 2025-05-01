package com.superbleep.rvgamvc.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class GameVersionId implements Serializable {
    private String id;
    private Long gameId;
}
