package io.arcpredict.service;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.protocol.Web3j;

@Service
@RequiredArgsConstructor
public class ArcEventListenerService {

    private static final Logger log =
        LoggerFactory.getLogger(
            ArcEventListenerService.class
        );

    private final Web3j web3j;

    private final BlockchainDecoderService
        blockchainDecoderService;

    private final MarketSyncService
        marketSyncService;

    private final BlockScannerService
        blockScannerService;

    @Value("${contracts.prediction-market-address}")
private String predictionMarketAddress;

    @PostConstruct
public void start() {

    log.info(
        "========================================"
    );

    log.info(
        "Prediction contract from Spring: {}",
        predictionMarketAddress
    );

    log.info(
        "========================================"
    );

    subscribeToLogs();

}

    private void subscribeToLogs() {

        web3j
            .blockFlowable(true)
            .subscribe(
                block -> {

                    log.debug(
                        "New block: {}",
                        block.getBlock().getNumber()
                    );

                    log.debug(
                        "Block transaction count: {}",
                        block.getBlock()
                             .getTransactions()
                             .size()
                    );

                    blockScannerService.scanBlock(
                        block.getBlock()
                    );

                },
                error -> {

                    log.error(
                        "Block flowable error",
                        error
                    );

                }
            );
    }
}