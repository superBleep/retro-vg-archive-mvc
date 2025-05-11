package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import lombok.experimental.Accessors;
import org.mapstruct.*;

import java.lang.annotation.Target;
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
