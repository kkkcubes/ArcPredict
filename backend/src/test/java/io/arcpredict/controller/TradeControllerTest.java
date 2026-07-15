package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import static org.mockito.ArgumentMatchers.any;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;

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
                    "$[0].trader"
                ).value(
                    "0xabc"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].amount"
                ).value(
                    100
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].marketId"
                ).value(
                    2
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].trader"
                ).value(
                    "0xdef"
                )
            )
            .andExpect(
                jsonPath(
                    "$[1].amount"
                ).value(
                    200
                )
            );

    }

    @Test
    void shouldReturnTradesPage() throws Exception {

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

        Page<TradeEntity> page =

            new PageImpl<>(
                trades,
                PageRequest.of(0, 20),
                trades.size()
            );

        when(
            tradeRepository.findAll(
                any(org.springframework.data.domain.Pageable.class)
            )
        ).thenReturn(
            page
        );

        mockMvc.perform(
        get("/api/trades/page")
            .param("page", "0")
            .param("size", "20")
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
            "$.content[0].marketId"
        ).value(
            1
        )
    )
    .andExpect(
        jsonPath(
            "$.content[1].marketId"
        ).value(
            2
        )
    )
    .andExpect(
        jsonPath(
            "$.totalElements"
        ).value(
            2
        )
    )
    .andExpect(
        jsonPath(
            "$.size"
        ).value(
            20
        )
    );

    }

    @Test
void shouldReturnTradesByMarket() throws Exception {

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
            .marketId(1L)
            .trader("0xdef")
            .yesPosition(false)
            .amount(200L)
            .txHash("0x222")
            .blockNumber(2L)
            .build()

    );

    when(
        tradeRepository.findByMarketId(
            1L
        )
    ).thenReturn(
        trades
    );

    mockMvc.perform(
            get("/api/trades/market/1")
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
                "$[0].trader"
            ).value(
                "0xabc"
            )
        )
        .andExpect(
            jsonPath(
                "$[1].marketId"
            ).value(
                1
            )
        )
        .andExpect(
            jsonPath(
                "$[1].trader"
            ).value(
                "0xdef"
            )
        );

}

@Test
void shouldReturnTradesByTrader() throws Exception {

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
            .trader("0xabc")
            .yesPosition(false)
            .amount(200L)
            .txHash("0x222")
            .blockNumber(2L)
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
            get("/api/trades/trader/0xabc")
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
                "$[0].trader"
            ).value(
                "0xabc"
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
                "$[1].trader"
            ).value(
                "0xabc"
            )
        )
        .andExpect(
            jsonPath(
                "$[1].marketId"
            ).value(
                2
            )
        );

}

}