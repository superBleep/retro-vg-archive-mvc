package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameVersionMapper {
    @Mapping(target = "gameId", source = "game")
    GameVersionDTO toDto(GameVersion gameVersion);
    @Mapping(target = "game", ignore = true)
    GameVersion toGameVersion(GameVersionDTO gameVersionDTO);

    default Long mapGameToId(Game game) {
        return game.getId();
    }
}
