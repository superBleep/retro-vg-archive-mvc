package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.mappers.EmulatorMapper;
import com.superbleep.rvgamvc.repositories.EmulatorRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmulatorServiceImpl implements EmulatorService {
    private final EmulatorRepository emulatorRepository;
    private final EmulatorMapper emulatorMapper;

    public EmulatorServiceImpl(EmulatorRepository emulatorRepository, EmulatorMapper emulatorMapper) {
        this.emulatorRepository = emulatorRepository;
        this.emulatorMapper = emulatorMapper;
    }

    @Override
    public EmulatorDTO save(EmulatorDTO emulatorDto, List<Platform> platforms) {
        if (platforms.isEmpty())
            throw new RuntimeException("An emulator must have at least one platform associated!");

        Emulator saved = emulatorRepository.save(emulatorMapper.toEmulator(emulatorDto));
        saved.setPlatforms(platforms);

        return emulatorMapper.toDto(saved);
    }

    @Override
    public EmulatorDTO findById(Long id) {
        Optional<Emulator> emulatorOptional = emulatorRepository.findById(id);

        if (emulatorOptional.isEmpty())
            throw new RuntimeException("Emulator not found!");

        return emulatorMapper.toDto(emulatorOptional.get());
    }

    @Override
    public List<EmulatorDTO> findByFilters(String name, String developer, String platformName, Integer releaseYear) {
        List<Emulator> emulators = new ArrayList<>();

        if (name == null && developer == null & platformName == null && releaseYear == null) {
            emulators = emulatorRepository.findAll();
        } else {
            Specification<Emulator> spec = EmulatorSpecifications.filterByFields(name, developer, platformName, releaseYear);

            emulators = emulatorRepository.findAll(spec);
        }

        return emulators.stream()
                .map(emulatorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        emulatorRepository.deleteById(id);
    }
}
