package io.arcpredict.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionConfirmedMessage {

    private String type;

    private String txHash;

    private Long marketId;

}