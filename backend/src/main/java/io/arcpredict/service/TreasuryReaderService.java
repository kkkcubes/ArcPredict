package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreasuryReaderService {

    private static final Logger log =
        LoggerFactory.getLogger(
            TreasuryReaderService.class
        );

    private final Web3j web3j;

    @Value("${contracts.treasury-address}")
    private String treasuryAddress;

    public Long getVaultBalance() {

        log.info(
            "Calling getVaultBalance()"
        );

        return callUint256Function(
            "getVaultBalance"
        );

    }

    public Long getTotalLiquidity() {

        log.info(
            "Calling totalLiquidity()"
        );

        return callUint256Function(
            "totalLiquidity"
        );

    }

    public Long getTotalLockedLiquidity() {

        log.info(
            "Calling totalLockedLiquidity()"
        );

        return callUint256Function(
            "totalLockedLiquidity"
        );

    }

    public Long getTotalReleasedLiquidity() {

        log.info(
            "Calling totalReleasedLiquidity()"
        );

        return callUint256Function(
            "totalReleasedLiquidity"
        );

    }

    private Long callUint256Function(
        String functionName
    ) {

        try {

            Function function =
                new Function(
                    functionName,
                    Collections.emptyList(),
                    List.of(
                        new TypeReference<Uint256>() {}
                    )
                );

            var ethCallResponse =
                web3j
                    .ethCall(
                        Transaction.createEthCallTransaction(
                            null,
                            treasuryAddress,
                            FunctionEncoder.encode(
                                function
                            )
                        ),
                        DefaultBlockParameterName.LATEST
                    )
                    .send();

            if (ethCallResponse.hasError()) {

                System.out.println(
                    "RPC Error Code: "
                    + ethCallResponse
                        .getError()
                        .getCode()
                );

                System.out.println(
                    "RPC Error Message: "
                    + ethCallResponse
                        .getError()
                        .getMessage()
                );

            }

            String response =
                ethCallResponse.getValue();

            log.debug(
                "Raw Response: {}",
                response
            );

            List<Type> output =
                FunctionReturnDecoder.decode(
                    response,
                    function.getOutputParameters()
                );

            if (output.isEmpty()) {
                return 0L;
            }

            return ((BigInteger) output.get(0).getValue())
                .longValue();

        } catch (Exception e) {

            log.error(
                "RPC failed for function: {}",
                functionName,
                e
            );

            log.warn(
                "Returning default value 0 for function: {}",
                functionName
            );

            return 0L;

        }

    }

}