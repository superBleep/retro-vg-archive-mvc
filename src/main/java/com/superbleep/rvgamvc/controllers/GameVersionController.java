package com.superbleep.rvgamvc.controllers;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import com.superbleep.rvgamvc.dto.GameDTO;
import com.superbleep.rvgamvc.dto.GameVersionDTO;
import com.superbleep.rvgamvc.filters.GameVersionFilter;
import com.superbleep.rvgamvc.services.game.GameService;
import com.superbleep.rvgamvc.services.gameVersion.GameVersionService;
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
public class GameVersionController {
    GameVersionService gameVersionService;
    GameService gameService;

    public GameVersionController(GameVersionService gameVersionService, GameService gameService) {
        this.gameVersionService = gameVersionService;
        this.gameService = gameService;
    }

    @RequestMapping(value = "/gameVersions", method = RequestMethod.GET)
    public String listAll(
            @RequestParam(defaultValue = "0") Integer page,
            GameVersionFilter filter, Model model
    ) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, 5, sort);

        Page<GameVersionDTO> gameVersionPage = gameVersionService.findByFilters(filter.getGameTitle(), filter.getVersion(),
                filter.getReleaseYear(), filter.getNotes(), pageable);

        Map<GameVersionId, String> gameTitles = new HashMap<>();
        gameVersionPage.forEach(dto -> {
            String title = gameService.findById(dto.getGameId()).getTitle();

            gameTitles.put(dto.getId(), title);
        });

        model.addAttribute("gameVersionPage", gameVersionPage);
        model.addAttribute("gameTitles", gameTitles);
        model.addAttribute("filter", filter);
        model.addAttribute("curPage", gameVersionPage.getNumber());
        model.addAttribute("totalPages", gameVersionPage.getTotalPages());

        return "gameVersions";
    }

    @RequestMapping(value = "/gameVersions/add", method = RequestMethod.GET)
    public String add(Model model) {
        List<GameDTO> allGames = gameService.findAll();

        GameVersionDTO gameVersionDTO = new GameVersionDTO();
        gameVersionDTO.setId(new GameVersionId(null, null));
        gameVersionDTO.setGameId(allGames.getFirst().getId());

        model.addAttribute("gameVersion", gameVersionDTO);
        model.addAttribute("games", allGames);

        return "addGameVersion";
    }

    @RequestMapping(value = "/gameVersions/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute GameVersionDTO gameVersionDTO) {
        gameVersionService.create(gameVersionDTO);

        return "redirect:/gameVersions";
    }

    @RequestMapping(value = "/gameVersions/edit/{id}_{gameId}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, @PathVariable Long gameId, Model model) {
        GameVersionId gameVersionId = new GameVersionId(id, gameId);

        GameVersionDTO gameVersionDTO = gameVersionService.findById(gameVersionId);
        gameVersionDTO.setVersion(id);

        List<GameDTO> allGames = gameService.findAll();

        model.addAttribute("gameVersion", gameVersionDTO);
        model.addAttribute("games", allGames);

        return "editGameVersion";
    }

    @RequestMapping(value = "/gameVersions/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute GameVersionDTO gameVersionDTO) {
        gameVersionDTO.setId(new GameVersionId(gameVersionDTO.getVersion(), gameVersionDTO.getGameId()));
            gameVersionService.update(gameVersionDTO);

        return "redirect:/gameVersions";
    }

    @RequestMapping(value = "/gameVersions/remove/{id}_{gameId}", method = RequestMethod.GET)
    public String remove(@PathVariable String id, @PathVariable Long gameId, Model model) {
        GameVersionId gameVersionId = new GameVersionId(id, gameId);

        GameVersionDTO gameVersionDTO = gameVersionService.findById(gameVersionId);
        String gameTitle = gameService.findById(gameVersionDTO.getGameId()).getTitle();


        model.addAttribute("gameVersion", gameVersionDTO);
        model.addAttribute("gameTitle", gameTitle);

        return "removeGameVersion";
    }

    @RequestMapping(value = "/gameVersions/delete", method = RequestMethod.POST)
    public String delete(@RequestParam String id, @RequestParam Long gameId) {
        GameVersionId gameVersionId = new GameVersionId(id, gameId);
        gameVersionService.deleteById(gameVersionId);

        return "redirect:/gameVersions";
    }
}
