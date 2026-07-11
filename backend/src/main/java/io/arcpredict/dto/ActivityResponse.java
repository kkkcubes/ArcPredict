package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {

    private Long id;

    private String eventType;

    private Long marketId;

    private String wallet;

    private Long amount;

    private String position;

    private String txHash;

    private Long blockNumber;

    private Instant timestamp;

    private String summary;

}