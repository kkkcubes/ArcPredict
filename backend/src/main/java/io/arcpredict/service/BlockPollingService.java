package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;

//@Service
@RequiredArgsConstructor
public class BlockPollingService {

    private final Web3j web3j;

    private final BlockScannerService
        blockScannerService;

    private BigInteger lastBlock;

    @Scheduled(fixedDelay = 5000)
    public void poll() {

        System.out.println(
            "POLLER RUNNING"
        );

        try {

            BigInteger latest =
                web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber();

            System.out.println(
                "LATEST BLOCK => "
                + latest
            );

            if (
                lastBlock == null
            ) {

                lastBlock = latest;

                return;
            }

            for (
                BigInteger i = lastBlock.add(
                    BigInteger.ONE
                );
                i.compareTo(latest) <= 0;
                i = i.add(
                    BigInteger.ONE
                )
            ) {

                EthBlock block =
                    web3j
                        .ethGetBlockByNumber(
                            new DefaultBlockParameterNumber(
                                i
                            ),
                            true
                        )
                        .send();

                blockScannerService
                    .scanBlock(
                        block.getBlock()
                    );
            }

            lastBlock = latest;

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}