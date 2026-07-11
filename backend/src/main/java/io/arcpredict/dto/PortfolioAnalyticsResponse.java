package io.arcpredict.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAnalyticsResponse {

    private String wallet;

    private Long totalInvested;

    private Long currentValue;

    private Long unrealizedPnL;

    private Long realizedPnL;

    private Double roi;

    private Long totalTrades;

    private Long yesPositions;

    private Long noPositions;

    private Double averageEntryPrice;

}