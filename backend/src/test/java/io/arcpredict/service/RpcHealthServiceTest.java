package io.arcpredict.service;

import io.arcpredict.dto.RpcHealthResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthChainId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RpcHealthServiceTest {

    @Mock
    private Web3j web3j;

    @SuppressWarnings("rawtypes")
    @Mock
    private Request blockRequest;

    @SuppressWarnings("rawtypes")
    @Mock
    private Request chainRequest;

    @InjectMocks
    private RpcHealthService rpcHealthService;

    @Test
    void shouldReturnHealthyRpcStatus() throws Exception {

        EthBlockNumber blockNumber =
            new EthBlockNumber();

        blockNumber.setResult(
            "0x3039"
        ); // 12345

        EthChainId chainId =
            new EthChainId();

        chainId.setResult(
            "0x312"
        ); // 786

        doReturn(
            blockRequest
        ).when(
            web3j
        ).ethBlockNumber();

        when(
            blockRequest.send()
        ).thenReturn(
            blockNumber
        );

        doReturn(
            chainRequest
        ).when(
            web3j
        ).ethChainId();

        when(
            chainRequest.send()
        ).thenReturn(
            chainId
        );

        RpcHealthResponse response =
            rpcHealthService.getRpcHealth();

        assertEquals(
            true,
            response.getConnected()
        );

        assertEquals(
            12345L,
            response.getLatestBlock()
        );

        assertEquals(
            786L,
            response.getChainId()
        );

        assertTrue(
            response.getLatency() >= 0
        );

    }

}