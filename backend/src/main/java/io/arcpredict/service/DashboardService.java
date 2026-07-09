package io.arcpredict.service;

import io.arcpredict.dto.DashboardResponse;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MarketRepository marketRepository;

    private final TradeRepository tradeRepository;

    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
            .latestBlock(
                marketRepository.findLatestBlock()
            )
            .totalMarkets(
                marketRepository.count()
            )
            .totalTrades(
                tradeRepository.count()
            )
            .totalVolume(
                marketRepository.findTotalVolume()
            )
            .activeMarkets(
                (long) marketRepository
                    .findByResolved(false)
                    .size()
            )
            .resolvedMarkets(
                (long) marketRepository
                    .findByResolved(true)
                    .size()
            )
            .build();
    }
}