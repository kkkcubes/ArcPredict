package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcHealthResponse {

    private Boolean connected;

    private Long latency;

    private Long chainId;

    private Long latestBlock;
}