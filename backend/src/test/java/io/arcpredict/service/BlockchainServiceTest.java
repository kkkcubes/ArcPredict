package io.arcpredict.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.web3j.protocol.Web3j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthChainId;

import java.math.BigInteger;

@ExtendWith(MockitoExtension.class)
class BlockchainServiceTest {

    @Mock
    private Web3j web3j;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private BlockchainService blockchainService;

   @Test
void shouldReturnFalseWhenBlockNumberFetchFails() throws Exception {

    when(
        web3j.ethBlockNumber()
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    assertFalse(
        blockchainService.isHealthy()
    );

}

@Test
void shouldThrowExceptionWhenClientVersionFails() throws Exception {

    when(
        web3j.web3ClientVersion()
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> blockchainService.getClientVersion()
        );

    assertEquals(
        "Failed to fetch client version",
        exception.getMessage()
    );

}

@Test
void shouldThrowExceptionWhenChainIdFails() throws Exception {

    when(
        web3j.ethChainId()
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> blockchainService.getChainId()
        );

    assertEquals(
        "Failed to fetch chain ID",
        exception.getMessage()
    );

}

@Test
void shouldThrowExceptionWhenLatestBlockFails() throws Exception {

    when(
        web3j.ethBlockNumber()
    ).thenThrow(
        new RuntimeException("RPC unavailable")
    );

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> blockchainService.getLatestBlock()
        );

    assertEquals(
        "Failed to fetch block",
        exception.getMessage()
    );

}

@Test
void shouldReturnLatestBlock() throws Exception {

    EthBlockNumber ethBlockNumber =
        mock(EthBlockNumber.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        mock(Request.class);

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

    Long latestBlock =
        blockchainService.getLatestBlock();

    assertEquals(
        123L,
        latestBlock
    );

    verify(
        metricsService
    ).setLatestBlockchainBlock(
        123L
    );

    verify(
    metricsService
).setRpcLatency(
    org.mockito.ArgumentMatchers.anyLong()
);

}

@Test
void shouldReturnClientVersion() throws Exception {

    Web3ClientVersion response =
        mock(Web3ClientVersion.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        mock(Request.class);

    when(
        web3j.web3ClientVersion()
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        response
    );

    when(
        response.getWeb3ClientVersion()
    ).thenReturn(
        "Geth/v1.0"
    );

    String clientVersion =
        blockchainService.getClientVersion();

    assertEquals(
        "Geth/v1.0",
        clientVersion
    );

}

@Test
void shouldReturnChainId() throws Exception {

    EthChainId response =
        mock(EthChainId.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        mock(Request.class);

    when(
        web3j.ethChainId()
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        response
    );

    when(
        response.getChainId()
    ).thenReturn(
        BigInteger.valueOf(8453)
    );

    Long chainId =
        blockchainService.getChainId();

    assertEquals(
        8453L,
        chainId
    );

}

@Test
void shouldReturnTrueWhenBlockchainIsHealthy() throws Exception {

    EthBlockNumber response =
        mock(EthBlockNumber.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    Request request =
        mock(Request.class);

    when(
        web3j.ethBlockNumber()
    ).thenReturn(
        request
    );

    when(
        request.send()
    ).thenReturn(
        response
    );

    assertEquals(
        true,
        blockchainService.isHealthy()
    );

}

}