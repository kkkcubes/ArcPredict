package io.arcpredict.service;

import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final TradeRepository tradeRepository;

    public List<LeaderboardEntry>
    getLeaderboard() {

        List<TradeEntity> trades =
            tradeRepository.findAll();

        Map<String, List<TradeEntity>>
            grouped =
            trades.stream()
                .collect(
                    Collectors.groupingBy(
                        TradeEntity::getTrader
                    )
                );

        return grouped.entrySet()
            .stream()
            .map(entry -> {

                long volume =
                    entry.getValue()
                        .stream()
                        .mapToLong(
                            TradeEntity::getAmount
                        )
                        .sum();

                long tradeCount =
                    entry.getValue()
                        .size();

                return LeaderboardEntry
                    .builder()
                    .wallet(
                        entry.getKey()
                    )
                    .totalVolume(
                        volume
                    )
                    .totalTrades(
                        tradeCount
                    )
                    .build();

            })
            .sorted(
                Comparator.comparing(
                    LeaderboardEntry::getTotalVolume
                ).reversed()
            )
            .toList();
    }
}