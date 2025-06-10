package com.superbleep.rvgamvc.services.gameVersion;

import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameVersionService {
    GameVersionDTO create(GameVersionDTO gameVersionDTO);
    GameVersionDTO findById(GameVersionId id);
    List<GameVersionDTO> findAllById(List<GameVersionId> gameVersionIds);
    Page<GameVersionDTO> findByFilters(String gameTitle, String version, Integer releaseYear, String notes, Pageable pageable);
    List<GameVersionDTO> findAll();
    GameVersionDTO update(GameVersionDTO gameVersionDTO);
    void deleteById(GameVersionId id);
}
