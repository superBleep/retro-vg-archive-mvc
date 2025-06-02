package com.superbleep.rvgamvc.services.gameVersion;

import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameVersionDTO;

import java.util.List;

public interface GameVersionService {
    GameVersionDTO create(GameVersionDTO gameVersionDTO);
    List<GameVersionDTO> findAllById(List<GameVersionId> gameVersionIds);
}
