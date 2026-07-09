package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketSentimentResponse {

    private Long marketId;

    private Long yesPool;

    private Long noPool;

    private Long totalPool;

    private Double yesPercentage;

    private Double noPercentage;
}