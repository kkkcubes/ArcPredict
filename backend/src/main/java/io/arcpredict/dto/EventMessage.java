package io.arcpredict.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventMessage {

    private String eventType;

    private Long marketId;

    private Long amount;

    private String wallet;

    private String timestamp;
}