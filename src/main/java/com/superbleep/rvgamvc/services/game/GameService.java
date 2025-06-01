package com.superbleep.rvgamvc.services.game;

import com.superbleep.rvgamvc.dto.GameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {
    GameDTO create(GameDTO gameDTO);
    GameDTO findById(Long id);
    List<GameDTO> findAllById(List<Long> id);
    Page<GameDTO> findByFilters(String title, String developer, String publisher, String genre, String platformName, Pageable pageable);
    List<GameDTO> findAll();
    GameDTO update(GameDTO gameDTO);
    void deleteById(Long id);
}
