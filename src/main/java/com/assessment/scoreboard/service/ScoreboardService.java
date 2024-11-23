package com.assessment.scoreboard.service;

import com.assessment.scoreboard.model.Player;

import java.util.List;

// Approach#1
public interface ScoreboardService {
    void addOrUpdatePlayer(String name, int score);
    void addMultiplePlayers(List<Player> players);
    List<Player> getTopPlayers();
}