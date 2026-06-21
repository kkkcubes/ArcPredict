package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCreatedEvent {

    private Long marketId;

    private String question;

    private String creator;

    private String txHash;

    private Long blockNumber;
}