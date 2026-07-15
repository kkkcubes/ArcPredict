package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;

@Service
@RequiredArgsConstructor
public class BlockchainService {

    private final Web3j web3j;

    public Long getLatestBlock() {

        try {

            return web3j
                .ethBlockNumber()
                .send()
                .getBlockNumber()
                .longValue();

        } catch (Exception e) {

            throw new RuntimeException(
                "Failed to fetch block",
                e
            );

        }

    }

    public String getClientVersion() {

        try {

            return web3j
                .web3ClientVersion()
                .send()
                .getWeb3ClientVersion();

        } catch (Exception e) {

            throw new RuntimeException(
                "Failed to fetch client version",
                e
            );

        }

    }

    public Long getChainId() {

        try {

            return web3j
                .ethChainId()
                .send()
                .getChainId()
                .longValue();

        } catch (Exception e) {

            throw new RuntimeException(
                "Failed to fetch chain ID",
                e
            );

        }

    }

    public boolean isHealthy() {

        try {

            web3j
                .ethBlockNumber()
                .send();

            return true;

        } catch (Exception e) {

            return false;

        }

    }

}