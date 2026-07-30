package io.arcpredict.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthCall;

import org.web3j.protocol.Web3j;

class MarketReaderServiceTest {

    private final Web3j web3j =
        mock(Web3j.class);

    private final MarketReaderService
        marketReaderService =
            new MarketReaderService(
                web3j
            );

            @SuppressWarnings({"rawtypes", "unchecked"})
private final Request request =
    mock(
        Request.class
    );

private final EthCall ethCall =
    mock(
        EthCall.class
    );

    @BeforeEach
    void setUp() throws Exception {

        Field field =
            MarketReaderService.class
                .getDeclaredField(
                    "predictionMarketAddress"
                );

        field.setAccessible(
            true
        );

        field.set(
            marketReaderService,
            "0x1234567890123456789012345678901234567890"
        );

    }

    @Test
void shouldReadMarket() throws Exception {

  when(
    web3j.ethCall(
        org.mockito.ArgumentMatchers.any(),
        org.mockito.ArgumentMatchers.eq(
            org.web3j.protocol.core.DefaultBlockParameterName.LATEST
        )
    )
).thenReturn(
    request
);

String response =
    "0x"
    + "0".repeat(320)
    + String.format(
        "%064x",
        1000
    )
    + "0".repeat(126)
    + "41";

when(
    request.send()
).thenReturn(
    ethCall
);

when(
    ethCall.getValue()
).thenReturn(
    response
);

MarketReaderService.MarketData
    marketData =
        marketReaderService.getMarket(
            1L
        );

        assertEquals(
    "A",
    marketData.category()
);

assertEquals(
    1000L,
    marketData.endTime()
);

}

}