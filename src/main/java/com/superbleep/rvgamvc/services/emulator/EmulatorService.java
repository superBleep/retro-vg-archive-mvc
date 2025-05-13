package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.dto.EmulatorDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EmulatorService {
    EmulatorDTO create(EmulatorDTO emulatorDto);
    EmulatorDTO findById(Long id);
    List<EmulatorDTO> findByFilters(String name, String developer, String platformName, Integer releaseYear);
    EmulatorDTO update(EmulatorDTO emulatorDTO);
    void deleteById(Long id);
}
