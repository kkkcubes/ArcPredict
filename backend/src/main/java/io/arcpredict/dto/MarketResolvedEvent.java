package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketResolvedEvent {

    private Long marketId;

    private Boolean outcome;

    private String txHash;

    private Long blockNumber;
}