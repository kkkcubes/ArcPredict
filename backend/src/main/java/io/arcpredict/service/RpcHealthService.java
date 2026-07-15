package io.arcpredict.service;

import io.arcpredict.dto.RpcHealthResponse;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;

@Service
@RequiredArgsConstructor
public class RpcHealthService {

    private static final Logger log =
        LoggerFactory.getLogger(
            RpcHealthService.class
        );

    private final Web3j web3j;

    public RpcHealthResponse getRpcHealth() {

        long start = System.currentTimeMillis();

        try {

            long latestBlock =
                web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber()
                    .longValue();

            long latency =
                System.currentTimeMillis() - start;

            long chainId =
                web3j
                    .ethChainId()
                    .send()
                    .getChainId()
                    .longValue();

            return RpcHealthResponse
                .builder()
                .connected(true)
                .latency(latency)
                .chainId(chainId)
                .latestBlock(latestBlock)
                .build();

        } catch (Exception e) {

            log.warn(
                "RPC health check failed",
                e
            );

            return RpcHealthResponse
                .builder()
                .connected(false)
                .latency(0L)
                .chainId(0L)
                .latestBlock(0L)
                .build();

        }

    }

}