package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    TradeController.class
)

@Import(
    SecurityConfig.class
)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    void shouldReturnTrades() throws Exception {

        List<TradeEntity> trades = List.of(

            TradeEntity.builder()
                .marketId(1L)
                .trader("0xabc")
                .yesPosition(true)
                .amount(100L)
                .txHash("0x111")
                .blockNumber(1L)
                .build(),

            TradeEntity.builder()
                .marketId(2L)
                .trader("0xdef")
                .yesPosition(false)
                .amount(200L)
                .txHash("0x222")
                .blockNumber(2L)
                .build()

        );

        when(
            tradeRepository.findAll()
        ).thenReturn(
            trades
        );

        mockMvc.perform(
                get("/api/trades")
            )
            .andExpect(
                status().isOk()
            );

    }

}