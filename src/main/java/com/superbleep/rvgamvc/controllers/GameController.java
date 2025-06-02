package com.superbleep.rvgamvc.controllers;

import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameDTO;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import com.superbleep.rvgamvc.dto.PlatformDTO;
import com.superbleep.rvgamvc.filters.GameFilter;
import com.superbleep.rvgamvc.services.game.GameService;
import com.superbleep.rvgamvc.services.gameVersion.GameVersionService;
import com.superbleep.rvgamvc.services.platform.PlatformService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {
    GameService gameService;
    PlatformService platformService;
    GameVersionService gameVersionService;

    public GameController(GameService gameService, PlatformService platformService, GameVersionService gameVersionService) {
        this.gameService = gameService;
        this.platformService = platformService;
        this.gameVersionService = gameVersionService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String listAll(
            @RequestParam(defaultValue = "0") Integer page,
            GameFilter filter, Model model
    ) {
        Sort sort = Sort.by("title").ascending();
        Pageable pageable = PageRequest.of(page, 5, sort);

        Page<GameDTO> gamePage = gameService.findByFilters(filter.getTitle(), filter.getDeveloper(), filter.getPublisher(),
                filter.getGenre(), filter.getPlatformName(), pageable);

        Map<Long, String> platformNames = new HashMap<>();
        gamePage.forEach(dto -> {
            String name = platformService.findById(dto.getPlatformId()).getName();

            platformNames.put(dto.getId(), name);
        });

        model.addAttribute("gamePage", gamePage);
        model.addAttribute("platforms", platformNames);
        model.addAttribute("filter", filter);
        model.addAttribute("curPage", gamePage.getNumber());
        model.addAttribute("totalPages", gamePage.getTotalPages());

        return "games";
    }

    @RequestMapping(value = "/games/add", method = RequestMethod.GET)
    public String add(Model model) {
        List<PlatformDTO> allPlatforms = platformService.findAll();
        Long startPlatformId = allPlatforms.getFirst().getId();

        GameVersionDTO gameVersionDTO = new GameVersionDTO();
        gameVersionDTO.setId(new GameVersionId(null, null));

        GameDTO gameDTO = new GameDTO();
        gameDTO.setPlatformId(startPlatformId);

        model.addAttribute("game", gameDTO);
        model.addAttribute("gameVersion", gameVersionDTO);
        model.addAttribute("platforms", allPlatforms);

        return "addGame";
    }

    @RequestMapping(value = "/games/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("game") GameDTO gameDTO, @ModelAttribute("gameVersion") GameVersionDTO gameVersionDTO) {
        GameDTO newGame = gameService.create(gameDTO);
        Long newGameId = newGame.getId();

        gameVersionDTO.setGameId(newGameId);
        gameVersionService.create(gameVersionDTO);

        return "redirect:/games";
    }

    @RequestMapping(value = "/games/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        GameDTO gameDTO = gameService.findById(id);
        List<PlatformDTO> allPlatforms = platformService.findAll();

        model.addAttribute("game", gameDTO);
        model.addAttribute("platforms", allPlatforms);

        return "editGame";
    }

    @RequestMapping(value = "/games/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute GameDTO gameDTO) {
        List<GameVersionId> gameVersionsIds = gameService.findById(gameDTO.getId()).getGameVersionIds();
        gameDTO.setGameVersionIds(gameVersionsIds);

        gameService.update(gameDTO);

        return "redirect:/games";
    }

    @RequestMapping(value = "/games/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable Long id, Model model) {
        GameDTO gameDTO = gameService.findById(id);
        String platformName = platformService.findById(gameDTO.getPlatformId()).getName();
        List<String> gameVersionIds = gameVersionService
                .findAllById(gameDTO.getGameVersionIds())
                .stream()
                .map(gv -> gv.getId().getId())
                .toList();

        model.addAttribute("game", gameDTO);
        model.addAttribute("platformName", platformName);
        model.addAttribute("gameVersionIds", String.join(", ", gameVersionIds));

        return "removeGame";
    }

    @RequestMapping(value = "/games/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Long id) {
        gameService.deleteById(id);

        return "redirect:/games";
    }
}
