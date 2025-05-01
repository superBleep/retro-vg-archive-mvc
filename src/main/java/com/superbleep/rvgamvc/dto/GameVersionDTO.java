package com.superbleep.rvgamvc.dto;

import com.superbleep.rvgamvc.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameVersionDTO {
    private Date release;
    private String notes;

    private Game game;
}
