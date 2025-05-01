package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameVersionMapper {
    GameVersionDTO toDto(GameVersion gameVersion);
    GameVersion toGameVersion(GameVersionDTO gameVersionDTO);
}
