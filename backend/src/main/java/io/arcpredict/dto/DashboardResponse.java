package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long latestBlock;

    private Long totalMarkets;

    private Long totalTrades;

    private Long totalVolume;

    private Long totalProtocolFees;

    private Long vaultBalance;

    private Long totalLiquidity;

    private Long totalLockedLiquidity;

    private Long totalReleasedLiquidity;

    private Long activeMarkets;

    private Long resolvedMarkets;

    private Long availableLiquidity;

private Double treasuryUtilization;

private String treasuryHealth;

}