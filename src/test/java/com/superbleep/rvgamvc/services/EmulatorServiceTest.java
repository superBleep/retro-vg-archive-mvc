package com.superbleep.rvgamvc.services;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.mappers.EmulatorMapper;
import com.superbleep.rvgamvc.repositories.EmulatorRepository;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
import com.superbleep.rvgamvc.services.emulator.EmulatorServiceImpl;
import com.superbleep.rvgamvc.services.emulator.EmulatorSpecifications;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmulatorServiceTest {
//    @Mock
//    EmulatorMapper emulatorMapper;
//    @Mock
//    EmulatorRepository emulatorRepository;
//    @Mock
//    PlatformRepository platformRepository;
//
//    @InjectMocks
//    EmulatorServiceImpl emulatorService;
//
//    private static final Date date = Date.from(LocalDate
//            .parse("2025-01-01")
//            .atStartOfDay(ZoneId.systemDefault()).
//            toInstant());
//
//    @Test
//    public void should_save_ifAllPlatformsExist() {
//        EmulatorDTO emulatorDTO = new EmulatorDTO();
//        emulatorDTO.setPlatformIds(List.of(1L, 2L));
//
//        Emulator toBeSaved = new Emulator();
//        Emulator saved = new Emulator();
//        EmulatorDTO expected = new EmulatorDTO();
//
//        List<Platform> platforms = List.of(
//                new Platform(1L, "name", "manufacturer", date, List.of(), List.of()),
//                new Platform(2L, "name", "manufacturer", date, List.of(), List.of())
//        );
//
//        when(platformRepository.existsById(anyLong())).thenReturn(true);
//        when(emulatorMapper.toEmulator(emulatorDTO)).thenReturn(toBeSaved);
//        when(platformRepository.findAllById(emulatorDTO.getPlatformIds())).thenReturn(platforms);
//        when(emulatorRepository.save(toBeSaved)).thenReturn(saved);
//        when(emulatorMapper.toDto(saved)).thenReturn(expected);
//
//        EmulatorDTO res = emulatorService.save(emulatorDTO);
//
//        assertEquals(expected, res);
//
//        verify(platformRepository, times(2)).existsById(anyLong());
//        verify(emulatorMapper).toEmulator(emulatorDTO);
//        verify(platformRepository).findAllById(emulatorDTO.getPlatformIds());
//        verify(emulatorRepository).save(toBeSaved);
//        verify(emulatorMapper).toDto(saved);
//    }
//
//    @Test
//    public void should_throwRuntimeException_ifPlatformIdsIsEmpty() {
//        EmulatorDTO emulatorDTO = new EmulatorDTO();
//        emulatorDTO.setPlatformIds(List.of());
//
//        assertThrows(RuntimeException.class, () -> emulatorService.save(emulatorDTO));
//    }
//
//    @Test
//    public void should_throwRuntimeException_ifPlatformsDoNotExist() {
//        EmulatorDTO emulatorDTO = new EmulatorDTO();
//        emulatorDTO.setPlatformIds(List.of(1L, 2L));
//
//        when(platformRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> emulatorService.save(emulatorDTO));
//
//        verify(platformRepository, times(3)).existsById(anyLong());
//    }
//
//    @Test
//    public void should_findById_ifEmulatorExists() {
//        Long emulatorId = 1L;
//        Emulator emulator = new Emulator();
//        emulator.setId(1L);
//        EmulatorDTO emulatorDTO = new EmulatorDTO();
//
//        when(emulatorRepository.findById(emulatorId)).thenReturn(Optional.of(emulator));
//        when(emulatorMapper.toDto(emulator)).thenReturn(emulatorDTO);
//
//        EmulatorDTO result = emulatorService.findById(emulatorId);
//
//        assertThat(result).isNotNull();
//
//        verify(emulatorRepository).findById(emulatorId);
//        verify(emulatorMapper).toDto(emulator);
//    }
//
//    @Test
//    public void should_throwRuntimeException_ifEmulatorNotFound() {
//        Long emulatorId = 1L;
//
//        when(emulatorRepository.findById(emulatorId)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> emulatorService.findById(emulatorId));
//    }
//
//    @Test
//    public void should_returnAll_ifAllFiltersAreNull() {
//        List<Emulator> allEmulators = List.of(new Emulator(), new Emulator());
//        List<EmulatorDTO> allDTOs = List.of(new EmulatorDTO(), new EmulatorDTO());
//
//        when(emulatorRepository.findAll()).thenReturn(allEmulators);
//        when(emulatorMapper.toDto(any(Emulator.class))).thenReturn(allDTOs.get(0), allDTOs.get(1));
//
//        List<EmulatorDTO> result = emulatorService.findByFilters(null, null, null, null);
//
//        assertEquals(result.size(), 2);
//
//        verify(emulatorRepository).findAll();
//        verify(emulatorMapper, times(2)).toDto(any());
//    }
//
//    @Test
//    public void should_returnFiltered_ifAllFiltersAreSpecified() {
//        String name = "emulator";
//        String developer = "developer";
//        String platformName = "platform";
//        Integer releaseYear = 2025;
//
//        Emulator emulator = new Emulator();
//        EmulatorDTO dto = new EmulatorDTO();
//
//        Specification<Emulator> spec = (root, query, cb) -> cb.conjunction();
//
//        mockStatic(EmulatorSpecifications.class).when(() ->
//                EmulatorSpecifications.filterByFields(name, developer, platformName, releaseYear)
//        ).thenReturn(spec);
//
//        when(emulatorRepository.findAll(spec)).thenReturn(List.of(emulator));
//        when(emulatorMapper.toDto(emulator)).thenReturn(dto);
//
//        List<EmulatorDTO> result = emulatorService.findByFilters(name, developer, platformName, releaseYear);
//
//        assertEquals(result.getFirst(), dto);
//
//        verify(emulatorRepository).findAll(spec);
//        verify(emulatorMapper).toDto(emulator);
//    }
//
//    @Test
//    public void should_delete_ifEmulatorFound() {
//        Long id = 1L;
//
//        when(emulatorRepository.existsById(id)).thenReturn(true);
//
//        emulatorService.deleteById(id);
//
//        verify(emulatorRepository).existsById(id);
//        verify(emulatorRepository).deleteById(id);
//    }
//
//    @Test
//    public void should_throwRuntimeException_ifDeleteEmulatorNotFound() {
//        Long id = 1L;
//
//        when(emulatorRepository.existsById(id)).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> emulatorService.deleteById(id));
//    }
}
