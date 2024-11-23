package com.assessment.scoreboard.controller;

import com.assessment.scoreboard.model.Player;
import com.assessment.scoreboard.service.ScoreboardService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Approach#1
@RestController
@RequestMapping("/api/scoreboard")
public class ScoreboardController2 {
    @Autowired
    private ScoreboardService2 scoreboardService2;

    @PostMapping("/add_player2")
    public ResponseEntity<String> addOrUpdatePlayer(
            @RequestParam String name,
            @RequestParam int score
    ) {
        scoreboardService2.addOrUpdatePlayer(name, score);
        System.out.println("controller2 addOrUpdatePlayer method called with: " + name + " - " + score);
        return ResponseEntity.ok("Player score updated successfully.");
    }

    @PostMapping("/add_players2")
    public ResponseEntity<String> addMultiplePlayers(@RequestBody List<Player> players) {
        scoreboardService2.addMultiplePlayers(players);
        return ResponseEntity.ok("Players scores updated successfully.");
    }

    @GetMapping("/get_top10_player2")
    public ResponseEntity<List<Player>> getTopPlayers() {
        System.out.println("controller2 getTopPlayers method called");
        return ResponseEntity.ok(scoreboardService2.getTopPlayers());
    }
}