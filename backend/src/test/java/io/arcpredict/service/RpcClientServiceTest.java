package io.arcpredict.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


import java.math.BigInteger;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
class RpcClientServiceTest {

    @Mock
    private Web3j web3j;

    @InjectMocks
    private RpcClientService rpcClientService;

    @Test
    void shouldThrowRuntimeExceptionWhenFetchingLatestBlockNumberFails() throws Exception {

        when(
            web3j.ethBlockNumber()
        ).thenThrow(
            new RuntimeException("RPC unavailable")
        );

        assertThrows(
            RuntimeException.class,
            () -> rpcClientService.getLatestBlockNumber()
        );

    }

    @Test
void shouldThrowRuntimeExceptionWhenFetchingTransactionReceiptFails() throws Exception {

    when(
        web3j.ethGetTransactionReceipt(
            "0x123"
        )
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    assertThrows(
        RuntimeException.class,
        () -> rpcClientService.getTransactionReceipt("0x123")
    );

}

@Test
void shouldThrowRuntimeExceptionWhenFetchingBlockFails() throws Exception {

    when(
        web3j.ethGetBlockByNumber(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.eq(true)
        )
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    assertThrows(
        RuntimeException.class,
        () -> rpcClientService.getBlock(
            java.math.BigInteger.ONE
        )
    );

}

@Test
void shouldThrowRuntimeExceptionWhenFetchingLogsFails() throws Exception {

    when(
        web3j.ethGetLogs(
            org.mockito.ArgumentMatchers.any()
        )
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    assertThrows(
        RuntimeException.class,
        () -> rpcClientService.getLogs(
            new org.web3j.protocol.core.methods.request.EthFilter()
        )
    );

}

@Test
void shouldGetBlockByParameter() throws Exception {

    @SuppressWarnings({"rawtypes", "unchecked"})
Request request =
    org.mockito.Mockito.mock(Request.class);

    EthBlock ethBlock =
        org.mockito.Mockito.mock(EthBlock.class);

    when(
        web3j.ethGetBlockByNumber(
            DefaultBlockParameterName.LATEST,
            true
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethBlock
    );

    EthBlock result =
        rpcClientService.getBlock(
            DefaultBlockParameterName.LATEST,
            true
        );

    assertSame(
        ethBlock,
        result
    );

}

@Test
void shouldGetLatestBlockNumber() throws Exception {

    EthBlockNumber ethBlockNumber =
        org.mockito.Mockito.mock(
            EthBlockNumber.class
        );

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        org.mockito.Mockito.mock(
            Request.class
        );

    when(
        web3j.ethBlockNumber()
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethBlockNumber
    );

    when(
        ethBlockNumber.getBlockNumber()
    ).thenReturn(
        BigInteger.valueOf(123)
    );

    BigInteger result =
        rpcClientService.getLatestBlockNumber();

    assertEquals(
    BigInteger.valueOf(123),
    result
);

}

@Test
void shouldGetTransactionReceipt() throws Exception {

    TransactionReceipt receipt =
        org.mockito.Mockito.mock(
            TransactionReceipt.class
        );

    EthGetTransactionReceipt response =
        org.mockito.Mockito.mock(
            EthGetTransactionReceipt.class
        );

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        org.mockito.Mockito.mock(
            Request.class
        );

    when(
        web3j.ethGetTransactionReceipt(
            "0x123"
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        response
    );

    when(
        response.getTransactionReceipt()
    ).thenReturn(
        Optional.of(
            receipt
        )
    );

    TransactionReceipt result =
        rpcClientService.getTransactionReceipt(
            "0x123"
        );

    assertSame(
        receipt,
        result
    );

}

@Test
void shouldGetBlockByNumber() throws Exception {

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        org.mockito.Mockito.mock(
            Request.class
        );

    EthBlock ethBlock =
        org.mockito.Mockito.mock(
            EthBlock.class
        );

    when(
        web3j.ethGetBlockByNumber(
            any(),
            eq(true)
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethBlock
    );

    EthBlock result =
        rpcClientService.getBlock(
            BigInteger.ONE
        );

    assertSame(
        ethBlock,
        result
    );

}

@Test
void shouldGetLogs() throws Exception {

    EthFilter filter =
        new EthFilter();

    EthLog ethLog =
        org.mockito.Mockito.mock(
            EthLog.class
        );

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        org.mockito.Mockito.mock(
            Request.class
        );

    when(
        web3j.ethGetLogs(
            filter
        )
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        ethLog
    );

    EthLog result =
        rpcClientService.getLogs(
            filter
        );

    assertSame(
        ethLog,
        result
    );

}

}