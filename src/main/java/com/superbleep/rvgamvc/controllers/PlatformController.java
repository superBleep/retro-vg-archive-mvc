package com.superbleep.rvgamvc.controllers;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.dto.EmulatorDTO;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import com.superbleep.rvgamvc.filters.PlatformFilter;
import com.superbleep.rvgamvc.repositories.GameRepository;
import com.superbleep.rvgamvc.services.emulator.EmulatorService;
import com.superbleep.rvgamvc.services.platform.PlatformService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PlatformController {
    PlatformService platformService;
    EmulatorService emulatorService;
    GameRepository gameRepository;

    public PlatformController(PlatformService platformService, EmulatorService emulatorService, GameRepository gameRepository) {
        this.platformService = platformService;
        this.emulatorService = emulatorService;
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
        List<EmulatorDTO> allEmulators = emulatorService.findAll();
        List<Game> allGames = gameRepository.findAll();

        List<Long> startEmulatorIds = new ArrayList<>();
        startEmulatorIds.add(allEmulators.getFirst().getId());

        List<Long> startGameIds = new ArrayList<>();
        startGameIds.add(allGames.getFirst().getId());

        PlatformDTO platformDTO = new PlatformDTO();

        platformDTO.setEmulatorIds(startEmulatorIds);
        platformDTO.setGameIds(startGameIds);

        model.addAttribute("platform", platformDTO);
        model.addAttribute("emulators", allEmulators);
        model.addAttribute("games", allGames);

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

        platformService.create(platformDTO);

        return "redirect:/platforms";
    }

    @RequestMapping(value = "/platforms/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        PlatformDTO platformDTO = platformService.findById(id);

        List<EmulatorDTO> allEmulators = emulatorService.findAll();
        List<Game> allGames = gameRepository.findAll();

        model.addAttribute("platform", platformDTO);
        model.addAttribute("emulators", allEmulators);
        model.addAttribute("games", allGames);

        return "editPlatform";
    }

    @RequestMapping(value = "/platforms/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute PlatformDTO platformDTO, @RequestParam String emulatorIdsString,
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

        platformService.update(platformDTO);

        return "redirect:/platforms";
    }

    @RequestMapping(value = "/platforms/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable Long id, Model model) {
        PlatformDTO platformDTO = platformService.findById(id);
        List<String> emulatorNames = emulatorService
                .findAllById(platformDTO.getEmulatorIds())
                .stream()
                .map(EmulatorDTO::getName)
                .toList();
        List<String> gameTitles = gameRepository
                .findAllById(platformDTO.getGameIds())
                .stream()
                .map(Game::getTitle)
                .toList();

        model.addAttribute("platform", platformDTO);
        model.addAttribute("emulatorNames", String.join(", ", emulatorNames));
        model.addAttribute("gameTitles", String.join(", ", gameTitles));

        return "removePlatform";
    }

    @Transactional
    @RequestMapping(value = "/platforms/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Long id) {
        PlatformDTO platformDTO = platformService.findById(id);

        emulatorService.findAllById(platformDTO.getEmulatorIds()).forEach(emulator -> {
            if (emulator.getPlatformIds().size() == 1)
                emulatorService.deleteById(emulator.getId());
        });

        platformService.deleteById(id);

        return "redirect:/platforms";
    }
}
