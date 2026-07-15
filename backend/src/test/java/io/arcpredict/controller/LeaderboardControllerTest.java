package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.service.LeaderboardService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    LeaderboardController.class
)

@Import(
    SecurityConfig.class
)
class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaderboardService leaderboardService;

    @Test
    void shouldReturnLeaderboard() throws Exception {

        List<LeaderboardEntry> leaderboard = List.of(

            LeaderboardEntry.builder()
                .wallet("0xabc")
                .totalVolume(10000L)
                .totalTrades(50L)
                .build(),

            LeaderboardEntry.builder()
                .wallet("0xdef")
                .totalVolume(8000L)
                .totalTrades(40L)
                .build()

        );

        when(
            leaderboardService.getLeaderboard()
        ).thenReturn(
            leaderboard
        );

        mockMvc.perform(
                get("/api/leaderboard")
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                content().contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].wallet"
                ).value(
                    "0xabc"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].totalVolume"
                ).value(
                    10000
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].totalTrades"
                ).value(
                    50
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].wallet"
                ).value(
                    "0xdef"
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].totalVolume"
                ).value(
                    8000
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].totalTrades"
                ).value(
                    40
                )
            );

    }

}