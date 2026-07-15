package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;

//@Service
@RequiredArgsConstructor
public class BlockPollingService {

    private static final Logger log =
        LoggerFactory.getLogger(
            BlockPollingService.class
        );

    private final Web3j web3j;

    private final BlockScannerService
        blockScannerService;

    private BigInteger lastBlock;

    @Scheduled(fixedDelay = 5000)
    public void poll() {

        log.info(
            "Blockchain poller running"
        );

        try {

            BigInteger latest =
                web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber();

            log.info(
                "Latest block: {}",
                latest
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

            log.error(
                "Error while polling blockchain",
                e
            );

        }

    }

}