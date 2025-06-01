package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.GameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {
    @Mapping(target = "platformId", source = "platform")
    @Mapping(target = "gameVersionIds", source = "gameVersions")
    GameDTO toDto(Game game);
    @Mapping(target = "platform", ignore = true)
    @Mapping(target = "gameVersions", ignore = true)
    Game toGame(GameDTO gameDTO);

    default Long mapPlatformToId(Platform platform) {
        return platform.getId();
    }

    default List<GameVersionId> mapGameVersionsToIds(List<GameVersion> gameVersions) {
        return gameVersions.stream()
                .map(GameVersion::getId).toList();
    }
}
