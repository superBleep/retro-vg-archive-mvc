package com.superbleep.rvgamvc.services.game;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.GameDTO;
import com.superbleep.rvgamvc.mappers.GameMapper;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.repositories.GameVersionRepository;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final GameVersionRepository gameVersionRepository;
    private final PlatformRepository platformRepository;
    private final GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameVersionRepository gameVersionRepository,
                           PlatformRepository platformRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameVersionRepository = gameVersionRepository;
        this.platformRepository = platformRepository;
        this.gameMapper = gameMapper;
    }

    private void checkGameVersionIds(List<GameVersionId> gameVersionIds) {
        logger.info("Checking game version ids");

        if (gameVersionIds.isEmpty())
            throw new RuntimeException("A game must have at least one version associated!");

        boolean validGameVersions = gameVersionIds.stream()
                .allMatch(gameVersionRepository::existsById);

        if (!validGameVersions) {
            List<GameVersionId> missing = gameVersionIds.stream()
                    .filter(id -> !gameVersionRepository.existsById(id))
                    .toList();

            throw new RuntimeException("Some game versions don't exist in the database: " + missing);
        }
    }

    private void checkPlatformId(Long platformId) {
        logger.info("Checking platform id");

        if (!platformRepository.existsById(platformId))
            throw new RuntimeException("Platform with id " + platformId + " doesn't exist in the database!");
    }

    private Game save(GameDTO dto) {
        logger.info("Saving game");

        Game newGame = gameMapper.toGame(dto);
        List<GameVersion> gameVersions = gameVersionRepository.findAllById(dto.getGameVersionIds());
        Platform platform = platformRepository.findById(dto.getPlatformId()).get();

        newGame.setGameVersions(gameVersions);
        newGame.setPlatform(platform);

        return gameRepository.save(newGame);
    }

    @Override
    @Transactional
    public GameDTO create(GameDTO gameDTO) {
        logger.info("Creating game");

        Long platformId = gameDTO.getPlatformId();

        checkPlatformId(platformId);

        Game savedGame = save(gameDTO);

        platformRepository.findById(platformId).get().getGames().add(savedGame);

        return gameMapper.toDto(savedGame);
    }

    @Override
    public GameDTO findById(Long id) {
        logger.info("Searching game by id");

        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isEmpty())
            throw new RuntimeException("Game not found!");

        return gameMapper.toDto(gameOptional.get());
    }

    @Override
    public List<GameDTO> findAllById(List<Long> ids) {
        logger.info("Searching all games by id");

        List<Game> games = gameRepository.findAllById(ids);

        return games.stream().map(gameMapper::toDto).toList();
    }

    @Override
    public Page<GameDTO> findByFilters(String title, String developer, String publisher, String genre, String platformName, Pageable pageable) {
        logger.info("Searching games by filters");

        Specification<Game> spec = GameSpecifications.filterByFields(title, developer, publisher, genre, platformName);
        Page<Game> page = gameRepository.findAll(spec, pageable);

        return page.map(gameMapper::toDto);
    }

    @Override
    public List<GameDTO> findAll() {
        logger.info("Searching all games");

        List<Game> games = gameRepository.findAll();

        return games.stream().map(gameMapper::toDto).toList();
    }

    @Override
    @Transactional
    public GameDTO update(GameDTO gameDTO) {
        logger.info("Updating game");

        Long id = gameDTO.getId();
        Long platformId = gameDTO.getPlatformId();
        List<GameVersionId> gameVersionIds = gameDTO.getGameVersionIds();

        if (!gameRepository.existsById(id))
            throw new RuntimeException("Game not found!");

        checkPlatformId(platformId);
        checkGameVersionIds(gameVersionIds);

        Game oldGame = gameRepository.findById(id).get();

        Platform oldPlatform = platformRepository.findByGameId(id);
        oldPlatform.getGames().remove(oldGame);
        platformRepository.save(oldPlatform);

        Platform curPlatform = platformRepository.findById(platformId).get();
        curPlatform.getGames().add(oldGame);
        platformRepository.save(curPlatform);

        Game savedGame = save(gameDTO);
        return gameMapper.toDto(savedGame);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting game");

        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isEmpty())
            throw new RuntimeException("Game not found!");
        else {
            Game game = gameOptional.get();

            gameVersionRepository.deleteAll(game.getGameVersions());

            Platform platform = platformRepository.findById(game.getPlatform().getId()).get();
            platform.getGames().remove(game);
            platformRepository.save(platform);

            gameRepository.deleteById(id);
        }
    }
}
