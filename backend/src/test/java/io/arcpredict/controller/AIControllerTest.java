package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.AIRequest;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;
import io.arcpredict.service.MarketService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

@WebMvcTest(
    AIController.class
)

@Import(
    SecurityConfig.class
)
class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MarketService marketService;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void shouldReturnVolumeAnswer() throws Exception {

        AnalyticsEntity analytics =

            AnalyticsEntity.builder()
                .totalVolume(50000L)
                .build();

        when(
            analyticsService.getAnalytics()
        ).thenReturn(
            analytics
        );

        AIRequest request =
            new AIRequest();

        request.setQuestion(
            "What is the current volume?"
        );

        mockMvc.perform(

                post("/api/ai/ask")

                    .contentType(
                        "application/json"
                    )

                    .content(
                        objectMapper.writeValueAsString(
                            request
                        )
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
                    "$.answer"
                ).value(
                    "Current volume: 50000"
                )
            );

    }

}