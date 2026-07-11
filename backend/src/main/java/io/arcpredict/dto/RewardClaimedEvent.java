package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardClaimedEvent {

    private Long marketId;

    private String trader;

    private Long amount;

    private String txHash;

    private Long blockNumber;

}