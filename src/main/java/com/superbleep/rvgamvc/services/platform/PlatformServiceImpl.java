package com.superbleep.rvgamvc.services.platform;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import com.superbleep.rvgamvc.mappers.PlatformMapper;
import com.superbleep.rvgamvc.repositories.EmulatorRepository;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;
    private final EmulatorRepository emulatorRepository;
    private final GameRepository gameRepository;
    private final PlatformMapper platformMapper;

    public PlatformServiceImpl(PlatformRepository platformRepository, EmulatorRepository emulatorRepository,
                               GameRepository gameRepository, PlatformMapper platformMapper) {
        this.platformRepository = platformRepository;
        this.emulatorRepository = emulatorRepository;
        this.gameRepository = gameRepository;
        this.platformMapper = platformMapper;
    }

    private void checkIds(List<Long> emulatorIds, List<Long> gameIds) {
        boolean validPlatforms = emulatorIds.stream()
                .allMatch(emulatorRepository::existsById);

        if(!validPlatforms) {
            List<Long> missing = emulatorIds.stream()
                    .filter(id -> !emulatorRepository.existsById(id))
                    .toList();

            throw new RuntimeException("Some emulators don't exist in the database: " + missing);
        }

        boolean validGames = gameIds.stream()
                .allMatch(gameRepository::existsById);

        if(!validGames) {
            List<Long> missing = gameIds.stream()
                    .filter(id -> !gameRepository.existsById(id))
                    .toList();

            throw new RuntimeException("Some games don't exist in the database: " + missing);
        }
    }

    private Platform save(PlatformDTO dto) {
        Platform newPlatform = platformMapper.toPlatform(dto);

        List<Emulator> emulators = emulatorRepository.findAllById(dto.getEmulatorIds());
        List<Game> games = gameRepository.findAllById(dto.getGameIds());

        newPlatform.setEmulators(emulators);
        newPlatform.setGames(games);

        return platformRepository.save(newPlatform);
    }

    @Override
    public PlatformDTO create(PlatformDTO platformDTO) {
        List<Long> emulatorIds = platformDTO.getEmulatorIds();
        List<Long> gameIds = platformDTO.getGameIds();

        Platform savedPlatform = save(platformDTO);

        emulatorRepository.findAllById(emulatorIds).forEach(emulator -> emulator.getPlatforms().add(savedPlatform));
        emulatorRepository.findAllById(gameIds).forEach(game -> game.getPlatforms().add(savedPlatform));

        return platformMapper.toDto(savedPlatform);
    }

    @Override
    public PlatformDTO findById(Long id) {
        Optional<Platform> platformOptional = platformRepository.findById(id);

        if (platformOptional.isEmpty())
            throw new RuntimeException("Platform not found!");

        return platformMapper.toDto(platformOptional.get());
    }

    @Override
    public Page<PlatformDTO> findByFilters(String name, String manufacturer, Integer releaseYear, Pageable pageable) {
        Specification<Platform> spec = PlatformSpecifications.filterByFields(name, manufacturer, releaseYear);
        Page<Platform> page = platformRepository.findAll(spec, pageable);

        return page.map(platformMapper::toDto);
    }

    @Override
    @Transactional
    public PlatformDTO update(PlatformDTO platformDTO) {
        Long id = platformDTO.getId();
        List<Long> emulatorIds = platformDTO.getEmulatorIds();
        List<Long> gameIds = platformDTO.getGameIds();

        if (!platformRepository.existsById(id))
            throw new RuntimeException("Platform not found!");

        checkIds(emulatorIds, gameIds);

        Platform oldPlatform = platformRepository.findById(id).get();

        List<Emulator> emulators = emulatorRepository.findAllById(emulatorIds);
        List<Game> games = gameRepository.findAllById(gameIds);

        emulatorRepository.findAllByPlatformId(id).forEach(emulator -> {
            emulator.getPlatforms().remove(oldPlatform);

            emulatorRepository.save(emulator);
        });
        emulators.forEach(curEmulator -> {
            curEmulator.getPlatforms().add(oldPlatform);

            emulatorRepository.save(curEmulator);
        });

        gameRepository.findAllByPlatformId(id).forEach(game -> {
            game.setPlatform(null);

            gameRepository.save(game);
        });
        games.forEach(game -> {
            game.setPlatform(oldPlatform);

            gameRepository.save(game);
        });

        Platform savedPlatform = save(platformDTO);
        return platformMapper.toDto(savedPlatform);
    }

    @Override
    public void deleteById(Long id) {
        if (!platformRepository.existsById(id))
            throw new RuntimeException("Platform not found!");

        platformRepository.deleteById(id);
    }
}
