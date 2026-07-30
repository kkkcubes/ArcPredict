package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.AnalyticsHistoryResponse;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

@WebMvcTest(
    AnalyticsController.class
)

@Import(
    SecurityConfig.class
)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void shouldReturnAnalytics() throws Exception {

        AnalyticsEntity analytics =
    AnalyticsEntity.builder()
        .totalMarkets(10L)
        .totalVolume(50000L)
        .totalTraders(120L)
        .resolvedMarkets(8L)
        .openInterest(25000L)
        .bullishPercentage(65.5)
        .bearishPercentage(34.5)
        .build();

        when(
            analyticsService.getAnalytics()
        ).thenReturn(
            analytics
        );

        mockMvc.perform(
                get("/api/analytics")
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
    jsonPath("$.totalVolume").value(50000)
)
            .andExpect(
    jsonPath("$.totalMarkets").value(10)
)
.andExpect(
    jsonPath("$.totalTraders").value(120)
)
.andExpect(
    jsonPath("$.resolvedMarkets").value(8)
)
.andExpect(
    jsonPath("$.openInterest").value(25000)
)
.andExpect(
    jsonPath("$.bullishPercentage").value(65.5)
)
.andExpect(
    jsonPath("$.bearishPercentage").value(34.5)
);

    }

    @Test
void shouldReturnAnalyticsHistory() throws Exception {

    AnalyticsHistoryResponse history =
        new AnalyticsHistoryResponse();


    when(
        analyticsService.getAnalyticsHistory()
    )
    .thenReturn(
        history
    );


    mockMvc.perform(
            get("/api/analytics/history")
        )
        .andExpect(
            status().isOk()
        )
        .andExpect(
            content()
                .contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
                )
        );

}

}