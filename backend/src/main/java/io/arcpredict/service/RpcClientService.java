package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RpcClientService {

    private final Web3j web3j;

    @Retryable(
        retryFor = IOException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    public EthBlock getBlock(
        DefaultBlockParameter blockParameter,
        boolean fullTransactions
    ) throws IOException {

        return web3j
            .ethGetBlockByNumber(
                blockParameter,
                fullTransactions
            )
            .send();

    }

    @Retryable(
        retryFor = IOException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    public TransactionReceipt getTransactionReceipt(
        String txHash
    ) throws IOException {

        return web3j
            .ethGetTransactionReceipt(txHash)
            .send()
            .getTransactionReceipt()
            .orElse(null);

    }

}