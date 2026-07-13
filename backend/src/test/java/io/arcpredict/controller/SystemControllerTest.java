package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.InfrastructureMetricsResponse;
import io.arcpredict.dto.SystemHealthResponse;
import io.arcpredict.service.SystemService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    SystemController.class
)

@Import(
    SecurityConfig.class
)
class SystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @Test
    void shouldReturnHealth()
            throws Exception {

        SystemHealthResponse response =

            SystemHealthResponse
                .builder()

                .status(
                    "UP"
                )

                .databaseConnected(
                    true
                )

                .rpcConnected(
                    true
                )

                .latestBlock(
                    123456L
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        when(
            systemService.getHealth()
        )
        .thenReturn(
            response
        );

        mockMvc

            .perform(

                get(
                    "/api/system/health"
                )

            )

            .andExpect(

                status().isOk()

            )

            .andExpect(

                content().contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
                )

            )

            .andExpect(

                jsonPath(
                    "$.status"
                )

                .value(
                    "UP"
                )

            )

            .andExpect(

                jsonPath(
                    "$.databaseConnected"
                )

                .value(
                    true
                )

            )

            .andExpect(

                jsonPath(
                    "$.rpcConnected"
                )

                .value(
                    true
                )

            )

            .andExpect(

                jsonPath(
                    "$.latestBlock"
                )

                .value(
                    123456L
                )

            )

            .andExpect(

                jsonPath(
                    "$.timestamp"
                )

                .exists()

            );

    }

    @Test
    void shouldReturnMetrics()
            throws Exception {

         InfrastructureMetricsResponse response =

    InfrastructureMetricsResponse
        .builder()

        .rpcConnected(
            true
        )

        .latestBlock(
            123456L
        )

        .rpcLatency(
            25L
        )

        .totalMarkets(
            10L
        )

        .totalTrades(
            100L
        )

        .totalEvents(
            500L
        )

        .websocketClients(
            8L
        )

        .uptimeSeconds(
            3600L
        )

        .usedMemoryMb(
            512L
        )

        .databaseStatus(
            "Connected"
        )

        .build();

        when(
    systemService.getMetrics()
)
.thenReturn(
    response
);

mockMvc

    .perform(

        get(
            "/api/system/metrics"
        )

    )

    .andExpect(

        status().isOk()

    )

    .andExpect(

        content().contentTypeCompatibleWith(
            MediaType.APPLICATION_JSON
        )

    )

    .andExpect(

    jsonPath(
        "$.rpcConnected"
    )

    .value(
        true
    )

)

.andExpect(

    jsonPath(
        "$.latestBlock"
    )

    .value(
        123456L
    )

)

.andExpect(

    jsonPath(
        "$.rpcLatency"
    )

    .value(
        25L
    )

)

.andExpect(

    jsonPath(
        "$.totalMarkets"
    )

    .value(
        10L
    )

)

.andExpect(

    jsonPath(
        "$.totalTrades"
    )

    .value(
        100L
    )

)

.andExpect(

    jsonPath(
        "$.totalEvents"
    )

    .value(
        500L
    )

)

.andExpect(

    jsonPath(
        "$.websocketClients"
    )

    .value(
        8L
    )

)

.andExpect(

    jsonPath(
        "$.uptimeSeconds"
    )

    .value(
        3600L
    )

)

.andExpect(

    jsonPath(
        "$.usedMemoryMb"
    )

    .value(
        512L
    )

)

.andExpect(

    jsonPath(
        "$.databaseStatus"
    )

    .value(
        "Connected"
    )

);

    }

}