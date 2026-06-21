package io.arcpredict.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioResponse {

    private String wallet;

    private Long totalInvested;

    private Long yesPositions;

    private Long noPositions;

    private Long totalTrades;
}