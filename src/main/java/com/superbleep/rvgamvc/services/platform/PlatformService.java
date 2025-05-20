package com.superbleep.rvgamvc.services.platform;

import com.superbleep.rvgamvc.dto.PlatformDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlatformService {
    PlatformDTO create(PlatformDTO platformDTO);
    PlatformDTO findById(Long id);
    Page<PlatformDTO> findByFilters(String name, String manufacturer, Integer releaseYear, Pageable pageable);
    PlatformDTO update(PlatformDTO platformDTO);
    void deleteById(Long id);
}
