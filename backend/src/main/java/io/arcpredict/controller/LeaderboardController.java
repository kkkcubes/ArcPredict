package io.arcpredict.controller;

import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.service.LeaderboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Leaderboard",
    description = "Leaderboard APIs"
)
@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService
        leaderboardService;

    @Operation(
        summary = "Get leaderboard",
        description = "Returns the top traders ranked by performance."
    )
    @GetMapping
    public List<LeaderboardEntry>
    leaderboard() {

        return leaderboardService
            .getLeaderboard();

    }

}