package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.dto.GameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDTO toDto(Game game);
    Game toGame(GameDTO gameDTO);
}
