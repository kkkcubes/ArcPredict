package io.arcpredict.service;

import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.entity.TradeEntity;

import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaderboardServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    void shouldBuildLeaderboard() {

        TradeEntity trade1 =
            TradeEntity.builder()
                .trader("0xalice")
                .amount(100L)
                .build();

        TradeEntity trade2 =
            TradeEntity.builder()
                .trader("0xalice")
                .amount(200L)
                .build();

        TradeEntity trade3 =
            TradeEntity.builder()
                .trader("0xbob")
                .amount(150L)
                .build();

        when(
            tradeRepository.findAll()
        ).thenReturn(
            List.of(
                trade1,
                trade2,
                trade3
            )
        );

        List<LeaderboardEntry> leaderboard =
            leaderboardService.getLeaderboard();

        assertEquals(
            2,
            leaderboard.size()
        );

        assertEquals(
            "0xalice",
            leaderboard.get(0).getWallet()
        );

        assertEquals(
            300L,
            leaderboard.get(0).getTotalVolume()
        );

        assertEquals(
            2L,
            leaderboard.get(0).getTotalTrades()
        );

        assertEquals(
            "0xbob",
            leaderboard.get(1).getWallet()
        );

        assertEquals(
            150L,
            leaderboard.get(1).getTotalVolume()
        );

        assertEquals(
            1L,
            leaderboard.get(1).getTotalTrades()
        );

    }

}