package io.arcpredict.service;

import java.math.BigInteger;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

@Service
@RequiredArgsConstructor
public class BlockPollingService {

    private static final Logger log =
        LoggerFactory.getLogger(
            BlockPollingService.class
        );

    private static final int MAX_BLOCKS_PER_POLL = 100;

    private final BlockchainService blockchainService;

    private final LogScannerService
        logScannerService;

    private final ReceiptScannerService
        receiptScannerService;

    private final BlockCheckpointService
        blockCheckpointService;

    @Value("${contracts.prediction-market-address}")
    private String predictionMarketAddress;

    @Scheduled(fixedDelay = 5000)
    public void poll() {

        log.info(
            "Blockchain poller running"
        );

        try {

            BigInteger latest =
                BigInteger.valueOf(
                    blockchainService
                        .getLatestBlock()
                );

            log.info(
                "Latest block: {}",
                latest
            );

            BigInteger lastBlock =
                blockCheckpointService
                    .getLastProcessedBlock();

            if (
                lastBlock == null
            ) {

                blockCheckpointService
                    .updateLastProcessedBlock(
                        latest
                    );

                return;
            }

            BigInteger endBlock =
                lastBlock.add(
                    BigInteger.valueOf(
                        MAX_BLOCKS_PER_POLL
                    )
                );

            if (
    endBlock.compareTo(
        latest
    ) > 0
) {
    endBlock = latest;
}

EthLog ethLog =
    logScannerService.getLogs(
        lastBlock.add(
            BigInteger.ONE
        ),
        endBlock,
        predictionMarketAddress
    );

for (
    EthLog.LogResult<?> logResult :
    ethLog.getLogs()
) {

    Log eventLog =
        (Log) logResult.get();

    receiptScannerService.scanReceipt(
        eventLog.getTransactionHash()
    );
}

blockCheckpointService
    .updateLastProcessedBlock(
        endBlock
    );

        } catch (Exception e) {

            log.error(
                "Error while polling blockchain",
                e
            );

        }

    }

}