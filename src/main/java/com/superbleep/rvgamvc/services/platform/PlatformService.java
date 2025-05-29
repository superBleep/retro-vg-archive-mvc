package com.superbleep.rvgamvc.services.platform;

import com.superbleep.rvgamvc.dto.PlatformDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlatformService {
    PlatformDTO create(PlatformDTO platformDTO);
    PlatformDTO findById(Long id);
    List<PlatformDTO> findAllById(List<Long> ids);
    Page<PlatformDTO> findByFilters(String name, String manufacturer, Integer releaseYear, Pageable pageable);
    List<PlatformDTO> findAll();
    PlatformDTO update(PlatformDTO platformDTO);
    void deleteById(Long id);
}
