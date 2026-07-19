package io.arcpredict.service;

import java.io.IOException;
import java.math.BigInteger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

@Service
@RequiredArgsConstructor
public class LogScannerService {

    private final RpcClientService rpcClientService;

    public EthLog getLogs(
        BigInteger fromBlock,
        BigInteger toBlock,
        String contractAddress
    ) throws IOException {

        EthFilter filter =
            new EthFilter(
                new DefaultBlockParameterNumber(
                    fromBlock
                ),
                new DefaultBlockParameterNumber(
                    toBlock
                ),
                contractAddress
            );

        return rpcClientService.getLogs(
            filter
        );

    }

}