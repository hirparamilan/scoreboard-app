package com.assessment.scoreboard.service.impl;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.ScoreboardService;
import org.springframework.stereotype.Service;

import java.util.*;

// Approach#1
@Service
public class ScoreboardServiceImpl implements ScoreboardService {
    private final Map<String, Integer> playerScores;
    private final PriorityQueue<Player> topPlayersHeap;

    public ScoreboardServiceImpl() {
        this.playerScores = new HashMap<>();
        this.topPlayersHeap = new PriorityQueue<>(Comparator.comparingInt(Player::getScore));
    }

    // Add or update player score
    // O(1) for updating the HashMap.
    // O(log k) for heap operations (insert/remove in a min-heap of size k).
    // Total: O(log k).
    public synchronized void addOrUpdatePlayer(String name, int score) {
        System.out.println("Service addOrUpdatePlayer method called with: " + name + " - " + score);

        // Update the player's score in the HashMap
        Integer existingScore = playerScores.put(name, score);

        // If player exists in heap, remove it
        if (existingScore != null) {
            // we have to compare by player's name and score because directly creating new player will not match here..
            topPlayersHeap.removeIf(player -> player.getName().equals(name) && player.getScore() == existingScore);
        }

        // Add the updated player to the heap
        topPlayersHeap.add(new Player(name, score));

        // If heap exceeds size 10, remove the smallest element
        if (topPlayersHeap.size() > 10) {
            topPlayersHeap.poll();
        }
    }

    // Add or update multiple players
    public void addMultiplePlayers(List<Player> players) {
        for (Player player : players) {
            // reuse the existing addOrUpdatePlayer method
            addOrUpdatePlayer(player.getName(), player.getScore());
        }
    }

    // Get top 10 players sorted by score (descending)
    // O(k log k) for sorting the heap (with k = 10).
    // Total: O(k log k), which is a constant time operation for k = 10.
    public List<Player> getTopPlayers() {
        System.out.println("Service getTopPlayers method called");

        // Convert the heap to a sorted list (descending order)
        List<Player> topPlayers = new ArrayList<>(topPlayersHeap);
        topPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());
        return topPlayers;
    }
}