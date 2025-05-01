package com.superbleep.rvgamvc.mappers;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmulatorMapper {
    EmulatorDTO toDto(Emulator emulator);
    Emulator toEmulator(EmulatorDTO emulatorDTO);
}
