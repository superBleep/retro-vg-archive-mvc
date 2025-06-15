package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.mappers.EmulatorMapper;
import com.superbleep.rvgamvc.repositories.EmulatorRepository;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
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
public class EmulatorServiceImpl implements EmulatorService {
    @Autowired
    @Value("${spring.datasource.platform}")
    private String dbms;
    private static final Logger logger = LoggerFactory.getLogger(EmulatorService.class);

    private final EmulatorRepository emulatorRepository;
    private final PlatformRepository platformRepository;
    private final EmulatorMapper emulatorMapper;

    public EmulatorServiceImpl(EmulatorRepository emulatorRepository, PlatformRepository platformRepository,
                               EmulatorMapper emulatorMapper) {
        this.emulatorRepository = emulatorRepository;
        this.platformRepository = platformRepository;
        this.emulatorMapper = emulatorMapper;
    }

    private void checkPlatformIds(List<Long> platformIds) {
        logger.info("Checking platform ids");

        if (platformIds.isEmpty())
            throw new RuntimeException("An emulator must have at least one platform associated!");

        boolean validPlatforms = platformIds.stream()
                .allMatch(platformRepository::existsById);

        if(!validPlatforms) {
            List<Long> missing = platformIds.stream()
                    .filter(id -> !platformRepository.existsById(id))
                    .toList();

            throw new RuntimeException("Some platforms don't exist in the database: " + missing);
        }
    }

    private Emulator save(EmulatorDTO dto) {
        logger.info("Saving emulator");

        Emulator newEmulator = emulatorMapper.toEmulator(dto);
        List<Platform> platforms = platformRepository.findAllById(dto.getPlatformIds());
        newEmulator.setPlatforms(platforms);

        return emulatorRepository.save(newEmulator);
    }

    @Override
    @Transactional
    public EmulatorDTO create(EmulatorDTO emulatorDTO) {
        logger.info("Creating emulator");

        List<Long> platformIds = emulatorDTO.getPlatformIds();
        checkPlatformIds(platformIds);

        Emulator savedEmulator = save(emulatorDTO);

        platformRepository.findAllById(platformIds).forEach(platform -> platform.getEmulators().add(savedEmulator));

        return emulatorMapper.toDto(savedEmulator);
    }

    @Override
    public EmulatorDTO findById(Long id) {
        logger.info("Searching emulator by id");

        Optional<Emulator> emulatorOptional = emulatorRepository.findById(id);

        if (emulatorOptional.isEmpty())
            throw new RuntimeException("Emulator not found!");

        return emulatorMapper.toDto(emulatorOptional.get());
    }

    @Override
    public List<EmulatorDTO> findAllById(List<Long> ids) {
        logger.info("Searching emulators by id");

        List<Emulator> emulators = emulatorRepository.findAllById(ids);

        return emulators.stream().map(emulatorMapper::toDto).toList();
    }

    @Override
    public Page<EmulatorDTO> findByFilters(String name, String developer, String platformName, Integer releaseYear, Pageable pageable) {
        logger.info("Searching emulators by filters");

        Specification<Emulator> spec = EmulatorSpecifications.filterByFields(name, developer, platformName, releaseYear, dbms);
        Page<Emulator> page = emulatorRepository.findAll(spec, pageable);

        return page.map(emulatorMapper::toDto);
    }

    @Override
    public List<EmulatorDTO> findAll() {
        logger.info("Searching all emulators");

        List<Emulator> emulators = emulatorRepository.findAll();

        return emulators.stream().map(emulatorMapper::toDto).toList();
    }

    @Override
    @Transactional
    public EmulatorDTO update(EmulatorDTO emulatorDTO) {
        logger.info("Updating emulator");

        Long id = emulatorDTO.getId();
        List<Long> platformIds = emulatorDTO.getPlatformIds();

        if (!emulatorRepository.existsById(id))
            throw new RuntimeException("Emulator not found!");

        checkPlatformIds(platformIds);

        Emulator oldEmulator = emulatorRepository.findById(id).get();
        List<Platform> platforms = platformRepository.findAllById(platformIds);

        platformRepository.findAllByEmulatorId(id).forEach(platform -> {
            platform.getEmulators().remove(oldEmulator);

            platformRepository.save(platform);
        });

        platforms.forEach(curPlatform -> {
            curPlatform.getEmulators().add(oldEmulator);

            platformRepository.save(curPlatform);
        });

        Emulator savedEmulator = save(emulatorDTO);
        return emulatorMapper.toDto(savedEmulator);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting emulator");

        Optional<Emulator> emulatorOptional = emulatorRepository.findById(id);

        if (emulatorOptional.isEmpty())
            throw new RuntimeException("Emulator not found!");
        else {
            Emulator emulator = emulatorOptional.get();

            platformRepository.findAllByEmulatorId(id).forEach(platform -> {
                platform.getEmulators().remove(emulator);

                platformRepository.save(platform);
            });

            emulatorRepository.deleteById(id);
        }
    }
}
