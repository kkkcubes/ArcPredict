package io.arcpredict.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

    @Mock
    private BlockchainService blockchainService;

    @InjectMocks
    private VerificationService verificationService;

    @Test
    void shouldReturnNetworkStatus() {

        when(
            blockchainService.getChainId()
        ).thenReturn(
            786L
        );

        when(
            blockchainService.getLatestBlock()
        ).thenReturn(
            12345L
        );

        when(
            blockchainService.isHealthy()
        ).thenReturn(
            true
        );

        Map<String, Object> response =

            verificationService.networkStatus();

        assertEquals(
            "Arc Testnet",
            response.get("network")
        );

        assertEquals(
            786L,
            response.get("chainId")
        );

        assertEquals(
            12345L,
            response.get("latestBlock")
        );

        assertEquals(
            true,
            response.get("healthy")
        );

    }

}