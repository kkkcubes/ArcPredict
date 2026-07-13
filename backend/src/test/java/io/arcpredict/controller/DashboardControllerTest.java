package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.DashboardResponse;
import io.arcpredict.service.DashboardService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@WebMvcTest(
    DashboardController.class
)

@Import(
    SecurityConfig.class
)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    void shouldReturnDashboard()
            throws Exception {

        DashboardResponse response =

            DashboardResponse
                .builder()

                .latestBlock(
                    123456L
                )

                .totalMarkets(
                    10L
                )

                .totalTrades(
                    100L
                )

                .totalVolume(
                    50000L
                )

                .totalProtocolFees(
                    500L
                )

                .vaultBalance(
                    100000L
                )

                .totalLiquidity(
                    200000L
                )

                .totalLockedLiquidity(
                    150000L
                )

                .totalReleasedLiquidity(
                    50000L
                )

                .activeMarkets(
                    8L
                )

                .resolvedMarkets(
                    2L
                )

                .availableLiquidity(
                    50000L
                )

                .treasuryUtilization(
                    75.5
                )

                .treasuryHealth(
                    "Healthy"
                )

                .build();

        when(
            dashboardService.getDashboard()
        )
        .thenReturn(
            response
        );

        mockMvc

    .perform(

        get(
            "/api/dashboard"
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
            "$.latestBlock"
        ).value(
            123456
        )

    )

    .andExpect(

        jsonPath(
            "$.totalMarkets"
        ).value(
            10
        )

    )

    .andExpect(

        jsonPath(
            "$.totalTrades"
        ).value(
            100
        )

    )

    .andExpect(

    jsonPath(
        "$.totalVolume"
    ).value(
        50000
    )

)

.andExpect(

    jsonPath(
        "$.totalProtocolFees"
    ).value(
        500
    )

)

.andExpect(

    jsonPath(
        "$.vaultBalance"
    ).value(
        100000
    )

)

.andExpect(

    jsonPath(
        "$.totalLiquidity"
    ).value(
        200000
    )

)

.andExpect(

    jsonPath(
        "$.totalLockedLiquidity"
    ).value(
        150000
    )

)

.andExpect(

    jsonPath(
        "$.totalReleasedLiquidity"
    ).value(
        50000
    )

)

.andExpect(

    jsonPath(
        "$.activeMarkets"
    ).value(
        8
    )

)

.andExpect(

    jsonPath(
        "$.resolvedMarkets"
    ).value(
        2
    )

)

.andExpect(

    jsonPath(
        "$.availableLiquidity"
    ).value(
        50000
    )

)

.andExpect(

    jsonPath(
        "$.treasuryUtilization"
    ).value(
        75.5
    )

)

.andExpect(

    jsonPath(
        "$.treasuryHealth"
    ).value(
        "Healthy"
    )

);

    }

}