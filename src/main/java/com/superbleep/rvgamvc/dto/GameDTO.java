package com.superbleep.rvgamvc.dto;

import com.superbleep.rvgamvc.domain.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private String title;
    private String developer;
    private String publisher;
    private String genre;
    private Platform platform;
}
