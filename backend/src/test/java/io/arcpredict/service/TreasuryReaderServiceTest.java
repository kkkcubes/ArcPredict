package io.arcpredict.service;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;

import org.web3j.protocol.Web3j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthCall;

import org.web3j.abi.FunctionEncoder;

import org.web3j.protocol.core.Response;

class TreasuryReaderServiceTest {

    private final Web3j web3j =
        mock(Web3j.class);

    private final TreasuryReaderService
        treasuryReaderService =
            new TreasuryReaderService(
                web3j
            );

    @BeforeEach
    void setUp() throws Exception {

        Field field =
            TreasuryReaderService.class
                .getDeclaredField(
                    "treasuryAddress"
                );

        field.setAccessible(
            true
        );

        field.set(
            treasuryReaderService,
            "0x1234567890123456789012345678901234567890"
        );

    }

    @Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldReturnZeroWhenRpcThrowsException() throws Exception {

    Request request =
        mock(
            Request.class
        );

    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenThrow(
        new RuntimeException(
            "RPC failed"
        )
    );

    assertEquals(
        0L,
        treasuryReaderService.getVaultBalance()
    );

}

@Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldReturnZeroForAllPublicMethodsWhenRpcThrowsException()
    throws Exception {

    Request request =
        mock(
            Request.class
        );

    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenThrow(
        new RuntimeException(
            "RPC failed"
        )
    );

    assertEquals(
        0L,
        treasuryReaderService.getTotalLiquidity()
    );

    assertEquals(
        0L,
        treasuryReaderService.getTotalLockedLiquidity()
    );

    assertEquals(
        0L,
        treasuryReaderService.getTotalReleasedLiquidity()
    );

}

@Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldReturnVaultBalanceWhenRpcSucceeds()
    throws Exception {

    Request request =
        mock(
            Request.class
        );

    EthCall ethCall =
        mock(
            EthCall.class
        );

    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethCall
    );

    when(
        ethCall.getValue()
    ).thenReturn(
        "0x" +
        "0000000000000000000000000000000000000000000000000000000000000064"
    );

    assertEquals(
        100L,
        treasuryReaderService.getVaultBalance()
    );

}

@Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldReturnLiquidityValuesWhenRpcSucceeds()
    throws Exception {

    Request request =
        mock(
            Request.class
        );

    EthCall ethCall =
        mock(
            EthCall.class
        );

    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethCall
    );

    when(
        ethCall.getValue()
    ).thenReturn(
        "0x" +
        "0000000000000000000000000000000000000000000000000000000000000064"
    );

    assertEquals(
        100L,
        treasuryReaderService.getTotalLiquidity()
    );

    assertEquals(
        100L,
        treasuryReaderService.getTotalLockedLiquidity()
    );

    assertEquals(
        100L,
        treasuryReaderService.getTotalReleasedLiquidity()
    );

}

@Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldReturnZeroWhenResponseIsEmpty()
    throws Exception {

    Request request =
        mock(
            Request.class
        );

    EthCall ethCall =
        mock(
            EthCall.class
        );

    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethCall
    );

    when(
        ethCall.getValue()
    ).thenReturn(
        "0x"
    );

    assertEquals(
        0L,
        treasuryReaderService.getVaultBalance()
    );

}

@Test
@SuppressWarnings({ "rawtypes", "unchecked" })
void shouldHandleRpcErrorResponse() throws Exception {

    Request request =
        mock(
            Request.class
        );

    EthCall ethCall =
        mock(
            EthCall.class
        );


    when(
        web3j.ethCall(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        )
    ).thenReturn(
        request
    );


    when(
        request.send()
    ).thenReturn(
        ethCall
    );


    when(
        ethCall.hasError()
    ).thenReturn(
        true
    );


    Response.Error error =
    new Response.Error(
        500,
        "RPC ERROR"
    );


    when(
        ethCall.getError()
    ).thenReturn(
        error
    );


    when(
        ethCall.getValue()
    ).thenReturn(
        "0x"
    );


    assertEquals(
        0L,
        treasuryReaderService.getVaultBalance()
    );

}

}