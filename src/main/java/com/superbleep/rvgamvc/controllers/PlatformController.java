package com.superbleep.rvgamvc.controllers;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.Platform;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import com.superbleep.rvgamvc.filters.PlatformFilter;
import com.superbleep.rvgamvc.repositories.EmulatorRepository;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.services.platform.PlatformService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class PlatformController {
    PlatformService platformService;
    EmulatorRepository emulatorRepository;
    GameRepository gameRepository;

    public PlatformController(PlatformService platformService, EmulatorRepository emulatorRepository, GameRepository gameRepository) {
        this.platformService = platformService;
        this.emulatorRepository = emulatorRepository;
        this.gameRepository = gameRepository;
    }

    @RequestMapping(value = "/platforms", method = RequestMethod.GET)
    public String listAll(
            @RequestParam(defaultValue = "0") Integer page,
            PlatformFilter filter, Model model
    ) {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, 5, sort);

        Page<PlatformDTO> platformPage = platformService.findByFilters(filter.getName(), filter.getManufacturer(),
                filter.getReleaseYear(), pageable);

        model.addAttribute("platformPage", platformPage);
        model.addAttribute("filter", filter);
        model.addAttribute("curPage", platformPage.getNumber());
        model.addAttribute("totalPages", platformPage.getTotalPages());

        return "platforms";
    }

    @RequestMapping(value = "/platforms/add", method = RequestMethod.GET)
    public String add(Model model) {
        List<Emulator> emulators = emulatorRepository.findAll();
        List<Game> games = gameRepository.findAll();

        PlatformDTO platformDTO = new PlatformDTO();
        platformDTO.setEmulatorIds(new ArrayList<>());
        platformDTO.setGameIds(new ArrayList<>());

        model.addAttribute("platform", platformDTO);
        model.addAttribute("emulators", emulators);
        model.addAttribute("games", games);

        return "addPlatform";
    }

    @RequestMapping(value = "/platforms/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute PlatformDTO platformDTO, @RequestParam String emulatorIdsString,
                         @RequestParam String gameIdsString) {
        if (emulatorIdsString != null && !emulatorIdsString.isBlank()) {
            List<Long> emulatorIds = Arrays.stream(emulatorIdsString.split(","))
                    .map(Long::parseLong)
                    .toList();

            platformDTO.setEmulatorIds(emulatorIds);
        }

        if (gameIdsString != null && !gameIdsString.isBlank()) {
            List<Long> gameIds = Arrays.stream(gameIdsString.split(","))
                    .map(Long::parseLong)
                    .toList();

            platformDTO.setGameIds(gameIds);
        }

        return "redirect:/platforms";
    }
}
