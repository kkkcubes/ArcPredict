package io.arcpredict.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletPositionResponse {

    private Long marketId;

    private Boolean yesPosition;

    private Long shares;

    private Long investedAmount;

    private Long currentValue;

    private Long claimableRewards;

    private Boolean claimed;

    private Boolean winner;

    private Boolean settled;

    private Long claimedAmount;

}