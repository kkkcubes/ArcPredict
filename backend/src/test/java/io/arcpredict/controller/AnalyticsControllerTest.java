package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

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
            );

    }

}