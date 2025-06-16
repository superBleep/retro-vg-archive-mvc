package com.superbleep.rvgamvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("postgres")
public class GameServiceIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "xX_basicUser_Xx", roles = {"REGULAR"})
    void listAll_shouldReturnList() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(view().name("games"))
                .andExpect(model().attributeExists("gamePage", "platforms", "filter", "curPage", "totalPages"));
    }

    @Test
    @WithMockUser(username = "xX_basicUser_Xx", roles = "REGULAR")
    void add_shouldReturnAccessDenied() throws Exception {
        mockMvc.perform(get("/games/add"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mod", roles = "MODERATOR")
    void add_shouldCreateGame() throws Exception {
        mockMvc.perform(post("/games/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Title")
                        .param("developer", "Developer")
                        .param("publisher", "Publisher")
                        .param("genre", "Genre")
                        .param("platformId", "1")
                        .param("version", "1.0.0")
                        .param("release", "1999-01-01")
                        .param("notes", "Notes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser(username = "xX_basicUser_Xx", roles = "REGULAR")
    void edit_shouldReturnAccessDenied() throws Exception {
        mockMvc.perform(get("/games/edit/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mod", roles = "MODERATOR")
    void update_shouldUpdateGame() throws Exception {
        mockMvc.perform(post("/games/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("title", "Title")
                        .param("developer", "Developer")
                        .param("publisher", "Publisher")
                        .param("genre", "Genre")
                        .param("platformId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser(username = "xX_basicUser_Xx", roles = "REGULAR")
    void remove_shouldReturnAccessDenied() throws Exception {
        mockMvc.perform(get("/games/remove/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mod", roles = "MODERATOR")
    void delete_shouldDeleteGame() throws Exception {
        mockMvc.perform(post("/games/delete")
                        .with(csrf())
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }
}
