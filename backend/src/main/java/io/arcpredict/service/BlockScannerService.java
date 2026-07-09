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

    @Value("${contracts.prediction-market.address}")
    private String predictionMarketAddress;

    public void scanBlock(
        EthBlock.Block block
    ) {

        log.debug(
    "Scanning block {}",
    block.getNumber()
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
                            (Transaction)
                                txResult.get();

                        if (
                            tx.getHash().equalsIgnoreCase(
                                "0x14e6e3c736fee849d288d7d80abeb9646a447a5d62d7766663e0864003b1054d"
                            )
                        ) {

                            log.debug(
    "Found test transaction"
);

                            log.debug(
    "Transaction recipient: {}",
    tx.getTo()
);
                        }

                        if (
                            tx.getTo() == null
                        ) {
                            return;
                        }

                        if (
                            !tx.getTo()
                                .equalsIgnoreCase(
                                    predictionMarketAddress
                                )
                        )
                        {
                            return;
                        }

                        log.info(
    "Prediction market transaction: {}",
    tx.getHash()
);

                        receiptScannerService
                            .scanReceipt(
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
