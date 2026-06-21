package io.arcpredict.controller;

import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LeaderboardController {

    private final LeaderboardService
        leaderboardService;

    @GetMapping
    public List<LeaderboardEntry>
    leaderboard() {

        return leaderboardService
            .getLeaderboard();
    }
}