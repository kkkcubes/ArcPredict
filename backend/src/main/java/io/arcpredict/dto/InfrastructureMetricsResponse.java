package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfrastructureMetricsResponse {

    private Boolean rpcConnected;

    private Long latestBlock;

    private Long rpcLatency;

    private Long totalMarkets;

    private Long totalTrades;

    private Long totalEvents;

    private Long websocketClients;

    private Long uptimeSeconds;

    private Long usedMemoryMb;

    private String databaseStatus;

}