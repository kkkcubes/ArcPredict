package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final BlockchainService
        blockchainService;

    public Map<String, Object>
    networkStatus() {

        Map<String, Object>
            response =
            new HashMap<>();

        response.put(
            "network",
            "Arc Testnet"
        );

        response.put(
            "chainId",
            blockchainService
                .getChainId()
        );

        response.put(
            "latestBlock",
            blockchainService
                .getLatestBlock()
        );

        response.put(
            "healthy",
            blockchainService
                .isHealthy()
        );

        return response;
    }
}