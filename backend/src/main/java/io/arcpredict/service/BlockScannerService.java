package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

@Service
@RequiredArgsConstructor
public class BlockScannerService {

    private static final Logger log =
        LoggerFactory.getLogger(
            BlockScannerService.class
        );

    private final Web3j web3j;

    private final ReceiptScannerService
        receiptScannerService;

    @Value("${contracts.prediction-market-address}")
    private String predictionMarketAddress;

    public void scanBlock(
        EthBlock.Block block
    ) {

        log.debug(
            "Scanning block {}",
            block.getNumber()
        );

        log.info(
            "Configured PredictionMarket: {}",
            predictionMarketAddress
        );

        try {

            EthBlock fullBlock =
                web3j
                    .ethGetBlockByNumber(
                        DefaultBlockParameter.valueOf(
                            block.getNumber()
                        ),
                        true
                    )
                    .send();

            log.debug(
                "Block transaction count: {}",
                fullBlock
                    .getBlock()
                    .getTransactions()
                    .size()
            );

            fullBlock
                .getBlock()
                .getTransactions()
                .forEach(txResult -> {

                    try {

                        Transaction tx =
                            (Transaction) txResult.get();

                        log.info(
    "Scanning transaction: {} -> {}",
    tx.getHash(),
    tx.getTo()
);

                        if (
                            tx.getTo() == null
                        ) {
                            return;
                        }

                        log.info(
                            "Transaction TO: {}",
                            tx.getTo()
                        );

                        if (
                            !tx.getTo().equalsIgnoreCase(
                                predictionMarketAddress
                            )
                        ) {

                            log.info(
                                "Skipping transaction"
                            );

                            return;

                        }

                        log.info(
                            "PredictionMarket transaction detected"
                        );

                        receiptScannerService
                            .scanReceipt(
                                tx.getHash()
                            );

                        log.info(
                            "PredictionMarket transaction: {}",
                            tx.getHash()
                        );

                    } catch (Exception e) {

                        log.error(
                            "Failed to process transaction",
                            e
                        );

                    }

                });

        } catch (Exception e) {

            log.error(
                "Failed to scan block {}",
                block.getNumber(),
                e
            );

        }

    }

}