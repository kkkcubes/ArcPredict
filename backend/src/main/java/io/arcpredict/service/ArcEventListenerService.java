package io.arcpredict.service;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;

@Service
@RequiredArgsConstructor
public class ArcEventListenerService {

    private final Web3j web3j;

    private final BlockchainDecoderService
        blockchainDecoderService;

    private final MarketSyncService
        marketSyncService;

    private final BlockScannerService
        blockScannerService;

    @Value("${contracts.prediction-market.address}")
    private String predictionMarketAddress;

    @PostConstruct
    public void start() {

        System.out.println(
            "PREDICTION CONTRACT => "
            + predictionMarketAddress
        );

        System.out.println(
            "LISTENER STARTED"
        );

        subscribeToLogs();
    }

    private void subscribeToLogs() {

        web3j
            .blockFlowable(true)
            .subscribe(
                block -> {

                    System.out.println(
                        "NEW BLOCK => "
                        + block.getBlock().getNumber()
                    );

                    System.out.println(
                        "BLOCK TX COUNT => "
                        + block.getBlock()
                               .getTransactions()
                               .size()
                    );

                    blockScannerService.scanBlock(
                        block.getBlock()
                    );

                },
                error -> {

                    System.out.println(
                        "FLOWABLE ERROR"
                    );

                    error.printStackTrace();
                }
            );
    }
}