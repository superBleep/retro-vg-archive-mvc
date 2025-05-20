package com.superbleep.rvgamvc.controllers;

import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.filters.EmulatorFilter;
import com.superbleep.rvgamvc.repositories.PlatformRepository;
import com.superbleep.rvgamvc.services.emulator.EmulatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class EmulatorController {
    EmulatorService emulatorService;
    PlatformRepository platformRepository;

    public EmulatorController(EmulatorService emulatorService, PlatformRepository platformRepository) {
        this.emulatorService = emulatorService;
        this.platformRepository = platformRepository;
    }

    @RequestMapping(value = "/emulators", method = RequestMethod.GET)
    public String listAll(
            @RequestParam(defaultValue = "0") Integer page,
            EmulatorFilter filter, Model model
    ) {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, 5, sort);

        Page<EmulatorDTO> emulatorPage = emulatorService.findByFilters(filter.getName(), filter.getDeveloper(),
                filter.getPlatformName(), filter.getReleaseYear(), pageable);

        Map<Long, String> platformNames = new HashMap<>();

        emulatorPage.forEach(dto -> {
            List<String> names = platformRepository.findAllById(dto.getPlatformIds())
                    .stream()
                    .map(Platform::getName)
                    .toList();

            platformNames.put(dto.getId(), String.join(", ", names));
        });

        model.addAttribute("emulatorPage", emulatorPage);
        model.addAttribute("platforms", platformNames);
        model.addAttribute("filter", filter);
        model.addAttribute("curPage", emulatorPage.getNumber());
        model.addAttribute("totalPages", emulatorPage.getTotalPages());

        return "emulators";
    }

    @RequestMapping(value = "/emulators/add", method = RequestMethod.GET)
    public String add(Model model) {
        EmulatorDTO emulatorDTO = new EmulatorDTO();
        emulatorDTO.setPlatformIds(new ArrayList<>());
        List<Platform> allPlatforms = platformRepository.findAll();

        model.addAttribute("emulator", emulatorDTO);
        model.addAttribute("platforms", allPlatforms);

        return "addEmulator";
    }

    @RequestMapping(value = "/emulators/create", method = RequestMethod.POST)
    public String create(@ModelAttribute EmulatorDTO emulatorDTO, @RequestParam String platformIdsString) {
        List<Long> platformIds = Arrays.stream(platformIdsString.split(","))
                .map(Long::parseLong)
                .toList();
        emulatorDTO.setPlatformIds(platformIds);

        emulatorService.create(emulatorDTO);

        return "redirect:/emulators";
    }

    @RequestMapping(value = "/emulators/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        EmulatorDTO emulatorDTO = emulatorService.findById(id);
        List<Platform> allPlatforms = platformRepository.findAll();

        model.addAttribute("emulator", emulatorDTO);
        model.addAttribute("platforms", allPlatforms);

        return "editEmulator";
    }

    @RequestMapping(value = "/emulators/update", method = RequestMethod.POST)
    public String update(@ModelAttribute EmulatorDTO emulatorDTO, @RequestParam String platformIdsString) {
        List<Long> platformIds = Arrays.stream(platformIdsString.split(","))
                .map(Long::parseLong)
                .toList();
        emulatorDTO.setPlatformIds(platformIds);

        emulatorService.update(emulatorDTO);

        return "redirect:/emulators";
    }
}
