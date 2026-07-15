package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.PortfolioAnalyticsResponse;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.service.PortfolioService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    PortfolioController.class
)

@Import(
    SecurityConfig.class
)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    void shouldReturnPortfolio() throws Exception {

        List<TradeEntity> trades = List.of(

            TradeEntity.builder()
                .marketId(1L)
                .trader("0xabc")
                .yesPosition(true)
                .amount(100L)
                .txHash("0x111")
                .blockNumber(1L)
                .timestamp(Instant.now())
                .build(),

            TradeEntity.builder()
                .marketId(2L)
                .trader("0xabc")
                .yesPosition(false)
                .amount(200L)
                .txHash("0x222")
                .blockNumber(2L)
                .timestamp(Instant.now())
                .build()

        );

        when(
            tradeRepository.findByTrader(
                "0xabc"
            )
        ).thenReturn(
            trades
        );

        mockMvc.perform(
                get("/api/portfolio/0xabc")
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
                jsonPath("$.wallet").value("0xabc")
            )
            .andExpect(
                jsonPath("$.totalInvested").value(300)
            )
            .andExpect(
                jsonPath("$.yesPositions").value(1)
            )
            .andExpect(
                jsonPath("$.noPositions").value(1)
            )
            .andExpect(
                jsonPath("$.totalTrades").value(2)
            );

    }

    @Test
    void shouldReturnPortfolioAnalytics() throws Exception {

        PortfolioAnalyticsResponse response =

            PortfolioAnalyticsResponse
                .builder()
                .wallet("0xabc")
                .totalInvested(300L)
                .currentValue(360L)
                .unrealizedPnL(60L)
                .realizedPnL(25L)
                .roi(20.0)
                .totalTrades(2L)
                .yesPositions(1L)
                .noPositions(1L)
                .averageEntryPrice(150.0)
                .build();

        when(
            portfolioService.getPortfolioAnalytics(
                "0xabc"
            )
        ).thenReturn(
            response
        );

        mockMvc.perform(
                get("/api/portfolio/analytics/0xabc")
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
        "$.wallet"
    ).value(
        "0xabc"
    )
)
.andExpect(
    jsonPath(
        "$.totalInvested"
    ).value(
        300
    )
)
.andExpect(
    jsonPath(
        "$.currentValue"
    ).value(
        360
    )
)
.andExpect(
    jsonPath(
        "$.unrealizedPnL"
    ).value(
        60
    )
)
.andExpect(
    jsonPath(
        "$.realizedPnL"
    ).value(
        25
    )
)
.andExpect(
    jsonPath(
        "$.roi"
    ).value(
        20.0
    )
)
.andExpect(
    jsonPath(
        "$.totalTrades"
    ).value(
        2
    )
)
.andExpect(
    jsonPath(
        "$.yesPositions"
    ).value(
        1
    )
)
.andExpect(
    jsonPath(
        "$.noPositions"
    ).value(
        1
    )
)
.andExpect(
    jsonPath(
        "$.averageEntryPrice"
    ).value(
        150.0
    )
);

    }

}