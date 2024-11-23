package com.assessment.scoreboard.service;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.impl.ScoreboardService2Impl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ScoreboardService2Test {

    @InjectMocks
    private ScoreboardService2Impl scoreboardService2;

    @Test
    void testAddOrUpdatePlayer_NewPlayer() {
        // Act: Add a new player
        scoreboardService2.addOrUpdatePlayer("Alice", 100);

        // Assert: Verify the player was added
        List<Player> topPlayers = scoreboardService2.getTopPlayers();
        assertEquals(1, topPlayers.size());
        assertEquals("Alice", topPlayers.get(0).getName());
        assertEquals(100, topPlayers.get(0).getScore());
    }

    @Test
    void testAddOrUpdatePlayer_UpdateExistingPlayer() {
        // Arrange: Add a player first
        scoreboardService2.addOrUpdatePlayer("Alice", 100);

        // Act: Update the player's score
        scoreboardService2.addOrUpdatePlayer("Alice", 150);

        // Assert: Verify the player's score was updated
        List<Player> topPlayers = scoreboardService2.getTopPlayers();
        assertEquals(1, topPlayers.size());
        assertEquals("Alice", topPlayers.get(0).getName());
        assertEquals(150, topPlayers.get(0).getScore());
    }

    @Test
    void testGetTopPlayers() {
        // Arrange: Add multiple players
        scoreboardService2.addOrUpdatePlayer("Alice", 100);
        scoreboardService2.addOrUpdatePlayer("Bob", 200);
        scoreboardService2.addOrUpdatePlayer("Charlie", 150);

        // Act: Get top players
        List<Player> topPlayers = scoreboardService2.getTopPlayers();

        // Assert: Verify top players are sorted by score in descending order
        assertEquals(3, topPlayers.size());
        assertEquals("Bob", topPlayers.get(0).getName()); // Highest score
        assertEquals(200, topPlayers.get(0).getScore());

        assertEquals("Charlie", topPlayers.get(1).getName());
        assertEquals(150, topPlayers.get(1).getScore());

        assertEquals("Alice", topPlayers.get(2).getName()); // Lowest score
        assertEquals(100, topPlayers.get(2).getScore());
    }

    @Test
    void testGetTopPlayers_LimitToTop10() {
        // Arrange: Add more than 10 players
        for (int i = 1; i <= 15; i++) {
            scoreboardService2.addOrUpdatePlayer("Player" + i, i * 10);
        }

        // Act: Get top 10 players
        List<Player> topPlayers = scoreboardService2.getTopPlayers();

        // Assert: Verify we get only the top 10 players
        assertEquals(10, topPlayers.size());
        assertEquals("Player15", topPlayers.get(0).getName());
        assertEquals(150, topPlayers.get(0).getScore());
    }
}
