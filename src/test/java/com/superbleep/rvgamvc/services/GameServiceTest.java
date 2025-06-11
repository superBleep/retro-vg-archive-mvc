package com.superbleep.rvgamvc.services;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.GameDTO;
import com.superbleep.rvgamvc.mappers.GameMapper;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.repositories.GameVersionRepository;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
import com.superbleep.rvgamvc.services.game.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameVersionRepository gameVersionRepository;
    @Mock
    private PlatformRepository platformRepository;
    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game game;
    private GameDTO gameDTO;
    private Platform platform;
    private GameVersion gameVersion;
    private GameVersionId gameVersionId;

    private final Long GAME_ID = 1L;
    private final Long PLATFORM_ID = 1L;

    @BeforeEach
    void setUp() {
        gameVersionId = new GameVersionId("1.0.0", 1L);

        gameVersion = new GameVersion();
        gameVersion.setId(gameVersionId);

        platform = new Platform();
        platform.setId(PLATFORM_ID);
        platform.setGames(new ArrayList<>());

        game = new Game();
        game.setId(GAME_ID);
        game.setTitle("Title");
        game.setPlatform(platform);
        game.setGameVersions(new ArrayList<>());

        gameDTO = new GameDTO();
        gameDTO.setId(GAME_ID);
        gameDTO.setTitle("Title");
        gameDTO.setPlatformId(1L);
        gameDTO.setGameVersionIds(List.of(gameVersionId));
    }

    @Test
    void create_shouldSaveGame() {
        when(platformRepository.existsById(PLATFORM_ID)).thenReturn(true);
        when(gameMapper.toGame(gameDTO)).thenReturn(game);
        when(platformRepository.findById(PLATFORM_ID)).thenReturn(Optional.of(platform));
        when(gameVersionRepository.findAllById(gameDTO.getGameVersionIds())).thenReturn(List.of(gameVersion));
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        GameDTO res = gameService.create(gameDTO);

        assertThat(res).isEqualTo(gameDTO);
        assertThat(platform.getGames()).contains(game);

        verify(platformRepository).existsById(PLATFORM_ID);
        verify(gameMapper).toGame(gameDTO);
        verify(platformRepository, times(2)).findById(PLATFORM_ID);
        verify(gameVersionRepository).findAllById(gameDTO.getGameVersionIds());
        verify(gameRepository).save(game);
        verify(gameMapper).toDto(game);
        verify(platformRepository, times(2)).findById(PLATFORM_ID);
    }

    @Test
    void create_shouldThrowIfPlatformDoesNotExist() {
        when(platformRepository.existsById(PLATFORM_ID)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> gameService.create(gameDTO));

        verify(platformRepository).existsById(PLATFORM_ID);
        verifyNoMoreInteractions(gameMapper, gameRepository, gameVersionRepository, platformRepository);
    }

    @Test
    void findById_shouldReturnGameDTO() {
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        GameDTO res = gameService.findById(GAME_ID);

        assertThat(res).isEqualTo(gameDTO);

        verify(gameRepository).findById(GAME_ID);
        verify(gameMapper).toDto(game);
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameService.findById(GAME_ID));

        verify(gameRepository).findById(GAME_ID);
    }

    @Test
    void findAllById_shouldReturnList() {
        when(gameRepository.findAllById(List.of(GAME_ID))).thenReturn(List.of(game));
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        List<GameDTO> res = gameService.findAllById(List.of(GAME_ID));

        assertThat(res).containsExactly(gameDTO);

        verify(gameRepository).findAllById(List.of(GAME_ID));
        verify(gameMapper).toDto(game);
    }

    @Test
    void findByFilters_shouldReturnFiltered() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Game> page = new PageImpl<>(List.of(game));

        when(gameRepository.findAll((Specification<Game>) any(), eq(pageable))).thenReturn(page);
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        Page<GameDTO> res = gameService.findByFilters("title", "developer", "publisher",
                "genre", "platform", pageable);

        assertThat(res.getContent()).containsExactly(gameDTO);

        verify(gameRepository).findAll((Specification<Game>) any(), eq(pageable));
        verify(gameMapper).toDto(game);
    }

    @Test
    void findAll_shouldReturnAllGames() {
        when(gameRepository.findAll()).thenReturn(List.of(game));
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        List<GameDTO> res = gameService.findAll();

        assertThat(res).containsExactly(gameDTO);

        verify(gameRepository).findAll();
        verify(gameMapper).toDto(game);
    }

    @Test
    void update_shouldUpdateSuccessfully() {
        Platform newPlatform = new Platform();
        newPlatform.setId(PLATFORM_ID + 1);
        newPlatform.setGames(new ArrayList<>());
        gameDTO.setPlatformId(newPlatform.getId());

        when(gameRepository.existsById(GAME_ID)).thenReturn(true);
        when(platformRepository.existsById(newPlatform.getId())).thenReturn(true);
        when(gameVersionRepository.existsById(gameVersionId)).thenReturn(true);
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(game));
        when(platformRepository.findByGameId(GAME_ID)).thenReturn(platform);
        when(platformRepository.findById(newPlatform.getId())).thenReturn(Optional.of(newPlatform));
        when(gameMapper.toGame(gameDTO)).thenReturn(game);
        when(gameVersionRepository.findAllById(gameDTO.getGameVersionIds())).thenReturn(List.of(gameVersion));
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(gameDTO);

        GameDTO res = gameService.update(gameDTO);

        assertThat(res).isEqualTo(gameDTO);
        assertThat(platform.getGames()).doesNotContain(game);
        assertThat(newPlatform.getGames()).contains(game);

        verify(gameRepository).existsById(GAME_ID);
        verify(platformRepository).existsById(newPlatform.getId());
        verify(gameVersionRepository).existsById(gameVersionId);
        verify(gameRepository).findById(GAME_ID);
        verify(platformRepository).findByGameId(GAME_ID);
        verify(platformRepository, times(2)).findById(newPlatform.getId());
        verify(platformRepository, times(2)).save(any());
        verify(gameMapper).toGame(gameDTO);
        verify(gameVersionRepository).findAllById(gameDTO.getGameVersionIds());
        verify(gameRepository).save(game);
        verify(gameMapper).toDto(game);
    }


    @Test
    void update_shouldThrowIfGameNotFound() {
        when(gameRepository.existsById(GAME_ID)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> gameService.update(gameDTO));

        verify(gameRepository).existsById(GAME_ID);
    }

    @Test
    void update_shouldThrowIfInvalidVersionId() {
        when(gameRepository.existsById(GAME_ID)).thenReturn(true);
        when(platformRepository.existsById(PLATFORM_ID)).thenReturn(true);
        when(gameVersionRepository.existsById(gameVersionId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> gameService.update(gameDTO));

        verify(gameRepository).existsById(GAME_ID);
        verify(platformRepository).existsById(PLATFORM_ID);
        verify(gameVersionRepository, times(2)).existsById(gameVersionId);
    }

    @Test
    void deleteById_shouldDeleteGame() {
        platform.getGames().add(game);

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(game));
        when(platformRepository.findById(PLATFORM_ID)).thenReturn(Optional.of(platform));

        gameService.deleteById(GAME_ID);

        verify(gameRepository).findById(GAME_ID);
        verify(gameVersionRepository).deleteAll(game.getGameVersions());
        verify(platformRepository).findById(PLATFORM_ID);
        verify(platformRepository).save(platform);
        verify(gameRepository).deleteById(GAME_ID);
    }


    @Test
    void deleteById_shouldThrowIfGameNotFound() {
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameService.deleteById(GAME_ID));

        verify(gameRepository).findById(GAME_ID);
    }
}
