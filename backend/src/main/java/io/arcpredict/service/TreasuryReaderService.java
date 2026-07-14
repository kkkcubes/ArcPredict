package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

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

    private final Web3j web3j;

    @Value("${contracts.treasury-address}")
    private String treasuryAddress;

    public Long getVaultBalance()
        throws Exception {

        return callUint256Function(
            "getVaultBalance"
        );
    }

    public Long getTotalLiquidity()
        throws Exception {

        return callUint256Function(
            "totalLiquidity"
        );
    }

    public Long getTotalLockedLiquidity()
        throws Exception {

        return callUint256Function(
            "totalLockedLiquidity"
        );
    }

    public Long getTotalReleasedLiquidity()
        throws Exception {

        return callUint256Function(
            "totalReleasedLiquidity"
        );
    }

    private Long callUint256Function(
        String functionName
    ) throws Exception {

        Function function =
            new Function(
                functionName,
                Collections.emptyList(),
                List.of(
                    new TypeReference<Uint256>() {}
                )
            );

        String response =
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
                .send()
                .getValue();

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
    }
}