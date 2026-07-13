package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHealthResponse {

    private String status;

    private Boolean databaseConnected;

    private Boolean rpcConnected;

    private Long latestBlock;

    private Long timestamp;

}