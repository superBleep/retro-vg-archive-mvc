package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformMapper {
    @Mapping(target = "emulatorIds", source = "emulators")
    @Mapping(target = "gameIds", source = "games")
    PlatformDTO toDto(Platform platform);
    @Mapping(target = "emulators", ignore = true)
    @Mapping(target = "games", ignore = true)
    Platform toPlatform(PlatformDTO platformDTO);

    default List<Long> mapEmulatorsToIds(List<Emulator> emulators) {
        return emulators.stream()
                .map(Emulator::getId).toList();
    }

    default List<Long> mapGamesToIds(List<Game> games) {
        return games.stream()
                .map(Game::getId).toList();
    }
}
