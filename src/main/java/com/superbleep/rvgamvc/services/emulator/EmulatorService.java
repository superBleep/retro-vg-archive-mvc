package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.dto.EmulatorDTO;

import java.util.List;

public interface EmulatorService {
    EmulatorDTO save(EmulatorDTO emulatorDto);
    EmulatorDTO findById(Long id);
    List<EmulatorDTO> findByFilters(String name, String developer, String platformName, Integer releaseYear);
    void deleteById(Long id);
}
