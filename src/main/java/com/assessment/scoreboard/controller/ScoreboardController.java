package com.assessment.scoreboard.controller;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Approach#1
@RestController
@RequestMapping("/api/scoreboard")
public class ScoreboardController {
    @Autowired
    private ScoreboardService scoreboardService;

    @PostMapping("/add_player")
    public ResponseEntity<String> addOrUpdatePlayer(
            @RequestParam String name,
            @RequestParam int score
    ) {
        scoreboardService.addOrUpdatePlayer(name, score);
        System.out.println("controller addOrUpdatePlayer method called with: " + name + " - " + score);
        return ResponseEntity.ok("Player score updated successfully.");
    }

    @PostMapping("/add_players")
    public ResponseEntity<String> addMultiplePlayers(@RequestBody List<Player> players) {
        scoreboardService.addMultiplePlayers(players);
        return ResponseEntity.ok("Players scores updated successfully.");
    }

    @GetMapping("/get_top10_player")
    public ResponseEntity<List<Player>> getTopPlayers() {
        System.out.println("controller getTopPlayers method called");
        return ResponseEntity.ok(scoreboardService.getTopPlayers());
    }
}