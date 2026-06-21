package io.arcpredict.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardEntry {

    private String wallet;

    private Long totalVolume;

    private Long totalTrades;
}