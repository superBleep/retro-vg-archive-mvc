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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
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
        if(!platformRepository.existsById(platformId))
            throw new RuntimeException("Platform with id " + platformId + " doesn't exist in the database!");
    }

    private Game save(GameDTO dto) {
        Game newGame = gameMapper.toGame(dto);
        List<GameVersion> gameVersions = gameVersionRepository.findAllById(dto.getGameVersionIds());
        Platform platform = platformRepository.findById(dto.getId()).get();

        newGame.setGameVersions(gameVersions);
        newGame.setPlatform(platform);

        return gameRepository.save(newGame);
    }

    @Override
    @Transactional
    public GameDTO create(GameDTO gameDTO) {
        Long platformId = gameDTO.getPlatformId();
        List<GameVersionId> gameVersionIds = gameDTO.getGameVersionIds();

        checkPlatformId(platformId);
        checkGameVersionIds(gameVersionIds);

        Game savedGame = save(gameDTO);

        platformRepository.findById(platformId).get().getGames().add(savedGame);
        gameVersionRepository.findAllById(gameVersionIds).forEach(gameVersion -> gameVersion.setGame(savedGame));

        return gameMapper.toDto(savedGame);
    }

    @Override
    public GameDTO findById(Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isEmpty())
            throw new RuntimeException("Game not found!");

        return gameMapper.toDto(gameOptional.get());
    }

    @Override
    public List<GameDTO> findAllById(List<Long> ids) {
        List<Game> games = gameRepository.findAllById(ids);

        return games.stream().map(gameMapper::toDto).toList();
    }

    @Override
    public Page<GameDTO> findByFilters(String title, String developer, String publisher, String genre, String platformName, Pageable pageable) {
        Specification<Game> spec = GameSpecifications.filterByFields(title, developer, publisher, genre, platformName);
        Page<Game> page = gameRepository.findAll(spec, pageable);

        return page.map(gameMapper::toDto);
    }

    @Override
    public List<GameDTO> findAll() {
        List<Game> games = gameRepository.findAll();

        return games.stream().map(gameMapper::toDto).toList();
    }

    @Override
    @Transactional
    public GameDTO update(GameDTO gameDTO) {
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
