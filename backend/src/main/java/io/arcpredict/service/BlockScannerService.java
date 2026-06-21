package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

@Service
@RequiredArgsConstructor
public class BlockScannerService {

    private final Web3j web3j;

    private final ReceiptScannerService
        receiptScannerService;

    @Value("${contracts.prediction-market.address}")
    private String predictionMarketAddress;

    public void scanBlock(
        EthBlock.Block block
    ) {

        System.out.println(
            "SCANNING BLOCK => "
            + block.getNumber()
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

            System.out.println(
                "BLOCK TX COUNT => "
                + fullBlock
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

                            System.out.println(
                                "FOUND TEST TX"
                            );

                            System.out.println(
                                "TO => "
                                + tx.getTo()
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

                        System.out.println(
                            "PREDICTION TX => "
                            + tx.getHash()
                        );

                        receiptScannerService
                            .scanReceipt(
                                tx.getHash()
                            );

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}