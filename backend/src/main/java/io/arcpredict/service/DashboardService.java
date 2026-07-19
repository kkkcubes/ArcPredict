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

    private final TreasuryReaderService treasuryReaderService;

    public DashboardResponse getDashboard() {

        Long totalLiquidity =
            treasuryReaderService
                .getTotalLiquidity();

        Long totalLockedLiquidity =
            treasuryReaderService
                .getTotalLockedLiquidity();

        Long availableLiquidity =
            totalLiquidity -
            totalLockedLiquidity;

        double treasuryUtilization =
            totalLiquidity == 0
                ? 0.0
                : (totalLockedLiquidity * 100.0)
                    / totalLiquidity;

        String treasuryHealth;

        if (treasuryUtilization < 50) {

            treasuryHealth = "Healthy";

        } else if (treasuryUtilization < 80) {

            treasuryHealth = "Warning";

        } else {

            treasuryHealth = "Critical";

        }

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

            .totalProtocolFees(
                marketRepository.findTotalProtocolFees()
            )

            .vaultBalance(
                treasuryReaderService.getVaultBalance()
            )

            .totalLiquidity(
                totalLiquidity
            )

            .totalLockedLiquidity(
                totalLockedLiquidity
            )

            .totalReleasedLiquidity(
                treasuryReaderService
                    .getTotalReleasedLiquidity()
            )

            .availableLiquidity(
                availableLiquidity
            )

            .treasuryUtilization(
                treasuryUtilization
            )

            .treasuryHealth(
                treasuryHealth
            )

            .activeMarkets(
                marketRepository.countByResolved(
                    false
                )
            )

            .resolvedMarkets(
                marketRepository.countByResolved(
                    true
                )
            )

            .build();

    }

}