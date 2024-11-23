package com.assessment.scoreboard.controller;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.ScoreboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ScoreboardControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ScoreboardService scoreboardService;

    @InjectMocks
    private ScoreboardController scoreboardController;

    private List<Player> mockTopPlayers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(scoreboardController).build(); // Standalone MockMvc
        mockTopPlayers = Arrays.asList(
                new Player("Alice", 100),
                new Player("Bob", 90),
                new Player("Charlie", 80)
        );
    }

    @Test
    void testAddOrUpdatePlayer() throws Exception {
        doNothing().when(scoreboardService).addOrUpdatePlayer(anyString(), anyInt());

        mockMvc.perform(post("/api/scoreboard/add_player")
                        .param("name", "Alice")
                        .param("score", "100")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Player score updated successfully."));

        System.out.println("Verifying addOrUpdatePlayer interaction...");
        verify(scoreboardService, times(1)).addOrUpdatePlayer("Alice", 100);
        verifyNoMoreInteractions(scoreboardService);
    }

    @Test
    void testGetTopPlayers() throws Exception {
        when(scoreboardService.getTopPlayers()).thenReturn(mockTopPlayers);

        mockMvc.perform(get("/api/scoreboard/get_top10_player")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].score").value(100))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[1].score").value(90))
                .andExpect(jsonPath("$[2].name").value("Charlie"))
                .andExpect(jsonPath("$[2].score").value(80));

        verify(scoreboardService, times(1)).getTopPlayers();
    }

}