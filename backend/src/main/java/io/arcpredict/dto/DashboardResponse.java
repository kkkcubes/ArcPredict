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

    private Long activeMarkets;

    private Long resolvedMarkets;
}