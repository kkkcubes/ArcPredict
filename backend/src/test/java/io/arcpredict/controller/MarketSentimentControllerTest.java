package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.MarketSentimentResponse;
import io.arcpredict.service.MarketSentimentService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    MarketSentimentController.class
)

@Import(
    SecurityConfig.class
)
class MarketSentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketSentimentService marketSentimentService;

    @Test
    void shouldReturnMarketSentiment() throws Exception {

        List<MarketSentimentResponse> response = List.of(

            MarketSentimentResponse.builder()
                .marketId(1L)
                .yesPool(600L)
                .noPool(400L)
                .totalPool(1000L)
                .yesPercentage(60.0)
                .noPercentage(40.0)
                .build()

        );

        when(
            marketSentimentService.getMarketSentiment()
        ).thenReturn(
            response
        );

        mockMvc.perform(
                get("/api/markets/sentiment")
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
                    "$[0].marketId"
                ).value(
                    1
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].yesPool"
                ).value(
                    600
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].noPool"
                ).value(
                    400
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].totalPool"
                ).value(
                    1000
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].yesPercentage"
                ).value(
                    60.0
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].noPercentage"
                ).value(
                    40.0
                )
            );

    }

}