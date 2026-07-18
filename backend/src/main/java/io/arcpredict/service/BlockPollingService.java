package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class BlockPollingService {

    private static final Logger log =
        LoggerFactory.getLogger(
            BlockPollingService.class
        );

    private final RpcClientService rpcClientService;

    private final BlockScannerService
        blockScannerService;

        private final BlockCheckpointService
    blockCheckpointService;


    @Scheduled(fixedDelay = 5000)
    public void poll() {

        log.info(
            "Blockchain poller running"
        );

        try {

            BigInteger latest =
    rpcClientService
        .getLatestBlockNumber();

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
        rpcClientService.getBlock(
            i
        );

    blockScannerService
        .scanBlock(
            block.getBlock()
        );

    blockCheckpointService
        .updateLastProcessedBlock(
            i
        );

}

        } catch (Exception e) {

            log.error(
                "Error while polling blockchain",
                e
            );

        }

    }

}