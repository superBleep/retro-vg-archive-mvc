package com.superbleep.rvgamvc.services.gameVersion;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import com.superbleep.rvgamvc.mappers.GameVersionMapper;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.repositories.GameVersionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameVersionServiceImpl implements GameVersionService {
    public final GameVersionRepository gameVersionRepository;
    public final GameRepository gameRepository;
    private final GameVersionMapper gameVersionMapper;

    public GameVersionServiceImpl(GameVersionRepository gameVersionRepository, GameRepository gameRepository,
                                  GameVersionMapper gameVersionMapper) {
        this.gameVersionRepository = gameVersionRepository;
        this.gameRepository = gameRepository;
        this.gameVersionMapper = gameVersionMapper;
    }

    private void checkGameId(Long gameId) {
        if (!gameRepository.existsById(gameId))
            throw new RuntimeException("Game with id " + gameId + " doesn't exist in the database!");
    }

    private GameVersion save(GameVersionDTO dto) {
        GameVersion newGameVersion = gameVersionMapper.toGameVersion(dto);
        Game game = gameRepository.findById(dto.getGameId()).get();

        newGameVersion.setGame(game);
        newGameVersion.setId(new GameVersionId(dto.getVersion(), game.getId()));

        return gameVersionRepository.save(newGameVersion);
    }

    @Override
    @Transactional
    public GameVersionDTO create(GameVersionDTO gameVersionDTO) {
        Long gameId = gameVersionDTO.getGameId();

        checkGameId(gameId);

        GameVersion savedGameVersion = save(gameVersionDTO);

        return gameVersionMapper.toDto(savedGameVersion);
    }

    public List<GameVersionDTO> findAllById(List<GameVersionId> ids) {
        List<GameVersion> gameVersions = gameVersionRepository.findAllById(ids);

        return gameVersions.stream().map(gameVersionMapper::toDto).toList();
    }
}
