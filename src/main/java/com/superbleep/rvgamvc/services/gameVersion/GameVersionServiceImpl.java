package com.superbleep.rvgamvc.services.gameVersion;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import com.superbleep.rvgamvc.mappers.GameVersionMapper;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.repositories.GameVersionRepository;
import com.superbleep.rvgamvc.services.emulator.EmulatorService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameVersionServiceImpl implements GameVersionService {
    @Autowired
    @Value("${spring.datasource.platform}")
    private String dbms;
    private static final Logger logger = LoggerFactory.getLogger(EmulatorService.class);

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
        logger.info("Checking game id");

        if (!gameRepository.existsById(gameId))
            throw new RuntimeException("Game with id " + gameId + " doesn't exist in the database!");
    }

    private GameVersion save(GameVersionDTO dto) {
        logger.info("Saving game");

        GameVersion newGameVersion = gameVersionMapper.toGameVersion(dto);
        Game game = gameRepository.findById(dto.getGameId()).get();

        newGameVersion.setGame(game);
        newGameVersion.setId(new GameVersionId(dto.getVersion(), game.getId()));

        return gameVersionRepository.save(newGameVersion);
    }

    @Override
    @Transactional
    public GameVersionDTO create(GameVersionDTO gameVersionDTO) {
        logger.info("Creating game");

        Long gameId = gameVersionDTO.getGameId();

        checkGameId(gameId);

        GameVersion savedGameVersion = save(gameVersionDTO);

        return gameVersionMapper.toDto(savedGameVersion);
    }

    @Override
    public GameVersionDTO findById(GameVersionId id) {
        logger.info("Searching game by id");

        Optional<GameVersion> gameVersionOptional = gameVersionRepository.findById(id);

        if (gameVersionOptional.isEmpty())
            throw new RuntimeException("Game version not found!");

        return gameVersionMapper.toDto(gameVersionOptional.get());
    }

    public List<GameVersionDTO> findAllById(List<GameVersionId> ids) {
        logger.info("Searching all games by id");

        List<GameVersion> gameVersions = gameVersionRepository.findAllById(ids);

        return gameVersions.stream().map(gameVersionMapper::toDto).toList();
    }

    @Override
    public Page<GameVersionDTO> findByFilters(String gameTitle, String version, Integer releaseYear, String notes, Pageable pageable) {
        logger.info("Searching games by filters");

        Specification<GameVersion> spec = GameVersionSpecifications.filterByFields(gameTitle, version, releaseYear, notes, dbms);
        Page<GameVersion> page = gameVersionRepository.findAll(spec, pageable);

        return page.map(gameVersionMapper::toDto);
    }

    @Override
    public List<GameVersionDTO> findAll() {
        logger.info("Searching all games");

        List<GameVersion> gameVersions = gameVersionRepository.findAll();

        return gameVersions.stream().map(gameVersionMapper::toDto).toList();
    }

    @Override
    @Transactional
    public GameVersionDTO update(GameVersionDTO gameVersionDTO) {
        logger.info("Updating game");

        Long gameId = gameVersionDTO.getGameId();

        checkGameId(gameId);

        GameVersion savedGameVersion = save(gameVersionDTO);

        return gameVersionMapper.toDto(savedGameVersion);
    }

    public void deleteById(GameVersionId id) {
        logger.info("Deleting game");

        Optional<GameVersion> gameVersionOptional = gameVersionRepository.findById(id);

        if (gameVersionOptional.isEmpty())
            throw new RuntimeException("Game version not found!");
        else {
            Game game = gameRepository.findByGameVersionId(id);

            if (game.getGameVersions().size() == 1)
                throw new RuntimeException("Game version is the only one remaining for its game!");

            game.getGameVersions().removeIf(gameVersion -> gameVersion.getId() == id);
            gameRepository.save(game);

            gameVersionRepository.deleteById(id);
        }
    }
}
