package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmulatorMapper {
    @Mapping(target = "platformIds", source = "platforms")
    EmulatorDTO toDto(Emulator emulator);
    @Mapping(target = "platforms", ignore = true)
    Emulator toEmulator(EmulatorDTO emulatorDTO);

    default List<Long> mapPlatformToIds(List<Platform> platforms) {
        return platforms.stream()
                .map(Platform::getId).toList();
    }
}
