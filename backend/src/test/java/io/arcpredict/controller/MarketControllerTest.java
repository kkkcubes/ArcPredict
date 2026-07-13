package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.service.MarketService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@WebMvcTest(
    MarketController.class
)

@Import(
    SecurityConfig.class
)
class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketService marketService;

    @Test
    void shouldReturnMarkets()
            throws Exception {

        MarketEntity market =

            new MarketEntity();

        market.setMarketId(
            1L
        );

        market.setQuestion(
            "Will BTC reach $200k?"
        );

        market.setCategory(
            "Crypto"
        );

        market.setCreator(
            "0x123456789"
        );

        market.setResolved(
            false
        );

        market.setOutcome(
            true
        );

        market.setYesPool(
            150L
        );

        market.setNoPool(
            75L
        );

        market.setTotalVolume(
            225L
        );

        market.setParticipants(
            12L
        );

        market.setProtocolFees(
            5L
        );

        market.setBlockNumber(
            123456L
        );

        market.setCreatedAt(
            Instant.parse(
                "2026-07-13T00:00:00Z"
            )
        );

        market.setUpdatedAt(
    Instant.parse(
        "2026-07-13T01:00:00Z"
    )
);

        when(

            marketService.getMarkets()

        )

        .thenReturn(

            List.of(
                market
            )

        );

        mockMvc

            .perform(

                get(
                    "/api/markets"
                )

            )

            .andExpect(

                status().isOk()

            )

            .andExpect(

                content().contentTypeCompatibleWith(
                    org.springframework.http.MediaType.APPLICATION_JSON
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].marketId"
                )

                .value(
                    1
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].question"
                )

                .value(
                    "Will BTC reach $200k?"
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].category"
                )

                .value(
                    "Crypto"
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].creator"
                )

                .value(
                    "0x123456789"
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].resolved"
                )

                .value(
                    false
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].outcome"
                )

                .value(
                    true
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].yesPool"
                )

                .value(
                    150
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].noPool"
                )

                .value(
                    75
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].totalVolume"
                )

                .value(
                    225
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].participants"
                )

                .value(
                    12
                )

            )

            .andExpect(

                jsonPath(
                    "$[0].protocolFees"
                )

                .value(
                    5
                )

            )

            .andExpect(

    jsonPath(
        "$[0].blockNumber"
    )

    .value(
        123456
    )

)

.andExpect(

    jsonPath(
        "$[0].createdAt"
    )

    .value(
        "2026-07-13T00:00:00Z"
    )

)

.andExpect(

    jsonPath(
        "$[0].updatedAt"
    )

    .value(
        "2026-07-13T01:00:00Z"
    )

);

    }

    @Test
void shouldReturnMarketById()
        throws Exception {

    MarketEntity market =

        new MarketEntity();

    market.setMarketId(
        1L
    );

    market.setQuestion(
        "Will BTC reach $200k?"
    );

    market.setCategory(
    "Crypto"
);

market.setCreator(
    "0x123456789"
);

market.setResolved(
    false
);

market.setOutcome(
    true
);

market.setYesPool(
    150L
);

market.setNoPool(
    75L
);

market.setTotalVolume(
    225L
);

market.setParticipants(
    12L
);

market.setProtocolFees(
    5L
);

market.setBlockNumber(
    123456L
);

market.setCreatedAt(
    Instant.parse(
        "2026-07-13T00:00:00Z"
    )
);

market.setUpdatedAt(
    Instant.parse(
        "2026-07-13T01:00:00Z"
    )
);

    when(

        marketService.getMarket(
            1L
        )

    )

    .thenReturn(

        market

    );

    mockMvc

    .perform(

        get(
            "/api/markets/1"
        )

    )

    .andExpect(

        status().isOk()

    )

    .andExpect(

    content().contentTypeCompatibleWith(
        org.springframework.http.MediaType.APPLICATION_JSON
    )

)

.andExpect(

    jsonPath(
        "$.marketId"
    )

    .value(
        1
    )

)

.andExpect(

    jsonPath(
        "$.question"
    )

    .value(
        "Will BTC reach $200k?"
    )

)

.andExpect(

    jsonPath(
        "$.category"
    )

    .value(
        "Crypto"
    )

)

.andExpect(

    jsonPath(
        "$.creator"
    )

    .value(
        "0x123456789"
    )

)

.andExpect(

    jsonPath(
        "$.resolved"
    )

    .value(
        false
    )

)

.andExpect(

    jsonPath(
        "$.outcome"
    )

    .value(
        true
    )

)

.andExpect(

    jsonPath(
        "$.yesPool"
    )

    .value(
        150
    )

)

.andExpect(

    jsonPath(
        "$.noPool"
    )

    .value(
        75
    )

)

.andExpect(

    jsonPath(
        "$.totalVolume"
    )

    .value(
        225
    )

)

.andExpect(

    jsonPath(
        "$.participants"
    )

    .value(
        12
    )

)

.andExpect(

    jsonPath(
        "$.protocolFees"
    )

    .value(
        5
    )

)

.andExpect(

    jsonPath(
        "$.blockNumber"
    )

    .value(
        123456
    )

)

.andExpect(

    jsonPath(
        "$.createdAt"
    )

    .value(
        "2026-07-13T00:00:00Z"
    )

)

    .andExpect(

        jsonPath(
            "$.updatedAt"
        )

        .value(
            "2026-07-13T01:00:00Z"
        )

);

}

@Test
void shouldReturnActiveMarkets()
        throws Exception {

    MarketEntity market =

        new MarketEntity();

    market.setMarketId(
        1L
    );

    market.setQuestion(
        "Will BTC reach $200k?"
    );

    market.setCategory(
    "Crypto"
);

market.setCreator(
    "0x123456789"
);

market.setResolved(
    false
);

market.setOutcome(
    true
);

market.setYesPool(
    150L
);

market.setNoPool(
    75L
);

market.setTotalVolume(
    225L
);

market.setParticipants(
    12L
);

market.setProtocolFees(
    5L
);

market.setBlockNumber(
    123456L
);

market.setCreatedAt(
    Instant.parse(
        "2026-07-13T00:00:00Z"
    )
);

market.setUpdatedAt(
    Instant.parse(
        "2026-07-13T01:00:00Z"
    )
);

    when(

    marketService.activeMarkets()

)

.thenReturn(

    List.of(
        market
    )

);

mockMvc

    .perform(

        get(
            "/api/markets/active"
        )

    )

    .andExpect(

        status().isOk()

    )

    .andExpect(

    content().contentTypeCompatibleWith(
        org.springframework.http.MediaType.APPLICATION_JSON
    )

)

.andExpect(

    jsonPath(
        "$[0].marketId"
    )

    .value(
        1
    )

)

.andExpect(

    jsonPath(
        "$[0].question"
    )

    .value(
        "Will BTC reach $200k?"
    )

)

.andExpect(

    jsonPath(
        "$[0].creator"
    )

    .value(
        "0x123456789"
    )

)

.andExpect(

    jsonPath(
        "$[0].resolved"
    )

    .value(
        false
    )

)

.andExpect(

    jsonPath(
        "$[0].outcome"
    )

    .value(
        true
    )

)

.andExpect(

    jsonPath(
        "$[0].yesPool"
    )

    .value(
        150
    )

)

.andExpect(

    jsonPath(
        "$[0].noPool"
    )

    .value(
        75
    )

)

.andExpect(

    jsonPath(
        "$[0].totalVolume"
    )

    .value(
        225
    )

)

.andExpect(

    jsonPath(
        "$[0].participants"
    )

    .value(
        12
    )

)

.andExpect(

    jsonPath(
        "$[0].blockNumber"
    )

    .value(
        123456
    )

)

.andExpect(

    jsonPath(
        "$[0].createdAt"
    )

    .value(
        "2026-07-13T00:00:00Z"
    )

)

.andExpect(

    jsonPath(
        "$[0].updatedAt"
    )

    .value(
        "2026-07-13T01:00:00Z"
    )

);

}

@Test
void shouldReturnMarketsPage()
        throws Exception {

    MarketEntity market =

        new MarketEntity();

    market.setMarketId(
        1L
    );

    market.setQuestion(
        "Will BTC reach $200k?"
    );

    market.setCategory(
    "Crypto"
);

market.setCreator(
    "0x123456789"
);

market.setResolved(
    false
);

market.setOutcome(
    true
);

market.setYesPool(
    150L
);

market.setNoPool(
    75L
);

market.setTotalVolume(
    225L
);

market.setParticipants(
    12L
);

market.setProtocolFees(
    5L
);

market.setBlockNumber(
    123456L
);

market.setCreatedAt(
    Instant.parse(
        "2026-07-13T00:00:00Z"
    )
);

market.setUpdatedAt(
    Instant.parse(
        "2026-07-13T01:00:00Z"
    )
);

    Page<MarketEntity> page =

    new PageImpl<>(

        List.of(
            market
        ),

        PageRequest.of(
            0,
            20
        ),

        1

    );

    when(

    marketService.getMarketsPage(

        any()

    )

)

.thenReturn(

    page

);

    mockMvc

    .perform(

        get(
            "/api/markets/page"
        )

    )

    .andExpect(

        status().isOk()

    )

    .andExpect(

    content().contentTypeCompatibleWith(
        org.springframework.http.MediaType.APPLICATION_JSON
    )

)

.andExpect(

    jsonPath(
        "$.content[0].marketId"
    )

    .value(
        1
    )

)

.andExpect(

    jsonPath(
        "$.content[0].question"
    )

    .value(
        "Will BTC reach $200k?"
    )

)

.andExpect(

    jsonPath(
        "$.content[0].category"
    )

    .value(
        "Crypto"
    )

)

.andExpect(

    jsonPath(
        "$.content[0].creator"
    )

    .value(
        "0x123456789"
    )

)

.andExpect(

    jsonPath(
        "$.content[0].resolved"
    )

    .value(
        false
    )

)

.andExpect(

    jsonPath(
        "$.content[0].outcome"
    )

    .value(
        true
    )

)

.andExpect(

    jsonPath(
        "$.content[0].yesPool"
    )

    .value(
        150
    )

)

.andExpect(

    jsonPath(
        "$.content[0].noPool"
    )

    .value(
        75
    )

)

.andExpect(

    jsonPath(
        "$.content[0].totalVolume"
    )

    .value(
        225
    )

)

.andExpect(

    jsonPath(
        "$.content[0].participants"
    )

    .value(
        12
    )

)

.andExpect(

    jsonPath(
        "$.content[0].protocolFees"
    )

    .value(
        5
    )

)

.andExpect(

    jsonPath(
        "$.content[0].blockNumber"
    )

    .value(
        123456
    )

)

.andExpect(

    jsonPath(
        "$.content[0].createdAt"
    )

    .value(
        "2026-07-13T00:00:00Z"
    )

)

.andExpect(

    jsonPath(
        "$.content[0].updatedAt"
    )

    .value(
        "2026-07-13T01:00:00Z"
    )

)


.andExpect(

    jsonPath(
        "$.totalElements"
    )

    .value(
        1
    )

)

.andExpect(

    jsonPath(
        "$.totalPages"
    )

    .value(
        1
    )

)

.andExpect(

    jsonPath(
        "$.size"
    )

    .value(
        20
    )

)

.andExpect(

    jsonPath(
        "$.number"
    )

    .value(
        0
    )

);

}

}