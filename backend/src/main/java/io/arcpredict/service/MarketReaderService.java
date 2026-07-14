package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketReaderService {

    private final Web3j web3j;

    @Value("${contracts.prediction-market-address}")
    private String predictionMarketAddress;

    public MarketData getMarket(
        Long marketId
    ) throws Exception {

        Function function =
            new Function(
                "getMarket",
                List.of(
                    new Uint256(
                        BigInteger.valueOf(
                            marketId
                        )
                    )
                ),
                List.of()
            );

        String response =
            web3j.ethCall(
                Transaction.createEthCallTransaction(
                    null,
                    predictionMarketAddress,
                    FunctionEncoder.encode(
                        function
                    )
                ),
                DefaultBlockParameterName.LATEST
            )
            .send()
            .getValue();

        String categoryHex =
            response.substring(
                response.length() - 128
            );

        String category =
            new String(
                Numeric.hexStringToByteArray(
                    categoryHex
                ),
                StandardCharsets.UTF_8
            )
            .replace("\u0000", "")
            .trim();

        Long endTime =
            new BigInteger(
                response.substring(
                    322,
                    386
                ),
                16
            )
            .longValue();

        return new MarketData(
            category,
            endTime
        );
    }

    public record MarketData(
        String category,
        Long endTime
    ) {}
}