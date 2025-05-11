package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformMapper {
    PlatformDTO toDto(Platform platform);
    Platform toPlatform(PlatformDTO platformDTO);
}
