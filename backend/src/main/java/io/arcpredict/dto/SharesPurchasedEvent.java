package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharesPurchasedEvent {

    private Long marketId;

    private String trader;

    private Boolean side;

    private Long amount;

    private String txHash;

    private Long blockNumber;
}