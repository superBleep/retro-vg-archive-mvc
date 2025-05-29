package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.dto.EmulatorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmulatorService {
    EmulatorDTO create(EmulatorDTO emulatorDto);
    EmulatorDTO findById(Long id);
    List<EmulatorDTO> findAllById(List<Long> id);
    Page<EmulatorDTO> findByFilters(String name, String developer, String platformName, Integer releaseYear, Pageable pageable);
    List<EmulatorDTO> findAll();
    EmulatorDTO update(EmulatorDTO emulatorDTO);
    void deleteById(Long id);
}
