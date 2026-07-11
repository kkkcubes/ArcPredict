package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private Long totalMarkets;

    private Long activeMarkets;

    private Long resolvedMarkets;

    private Long totalTrades;

    private Long totalVolume;

    private Long yesPool;

    private Long noPool;
}