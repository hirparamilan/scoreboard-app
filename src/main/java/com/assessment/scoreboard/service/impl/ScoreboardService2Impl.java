package com.assessment.scoreboard.service.impl;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.ScoreboardService2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Approach#1
@Service
public class ScoreboardService2Impl implements ScoreboardService2 {
    private final Map<String, Integer> playerScores;

    public ScoreboardService2Impl() {
        this.playerScores = new HashMap<>();
    }

    // Add or update player score
    // Adding or updating a player's score is efficient (O(1) for HashMap operations).
    public synchronized void addOrUpdatePlayer(String name, int score) {
        System.out.println("Service2 addOrUpdatePlayer method called with: " + name + " - " + score);
        playerScores.put(name, score);
    }

    // Add or update multiple players
    public void addMultiplePlayers(List<Player> players) {
        for (Player player : players) {
            // reuse the existing addOrUpdatePlayer method
            addOrUpdatePlayer(player.getName(), player.getScore());
        }
    }

    // Get top 10 players sorted by score (descending)
    // Retrieving the top 10 players involves sorting all players (O(n log n)) and limiting to 10, which is acceptable unless you have millions of players.
    public List<Player> getTopPlayers() {
        System.out.println("Service2 getTopPlayers method called");
        return playerScores.entrySet()
                .stream()
                .map(entry -> new Player(entry.getKey(), entry.getValue())) // Convert to Player objects
                .sorted(Comparator.comparingInt(Player::getScore).reversed()) // Sort by score descending
                .limit(10) // Limit to top 10
                .collect(Collectors.toList());
    }
}