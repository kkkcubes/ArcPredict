package io.arcpredict.service;

import io.arcpredict.dto.MarketCreatedEvent;
import io.arcpredict.dto.MarketResolvedEvent;
import io.arcpredict.dto.SharesPurchasedEvent;
import io.arcpredict.dto.RewardClaimedEvent;

import io.arcpredict.util.ContractEvents;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.protocol.core.methods.response.Log;

@Service
public class BlockchainDecoderService {

    private static final Logger log =
    LoggerFactory.getLogger(
        BlockchainDecoderService.class
    );

    public String eventType(
        Log log
    ) {

        if (
            log.getTopics().isEmpty()
        ) {
            return "UNKNOWN";
        }

        String topic0 =
            log.getTopics().get(0);

        if (
            ContractEvents.MARKET_CREATED
                .equalsIgnoreCase(topic0)
        ) {
            return "MARKET_CREATED";
        }

        if (
            ContractEvents.SHARES_PURCHASED
                .equalsIgnoreCase(topic0)
        ) {
            return "SHARES_PURCHASED";
        }

        if (
            ContractEvents.MARKET_RESOLVED
                .equalsIgnoreCase(topic0)
        ) {
            return "MARKET_RESOLVED";
        }

        if (
    ContractEvents.REWARD_CLAIMED
        .equalsIgnoreCase(topic0)
) {
    return "REWARD_CLAIMED";
}

        return "UNKNOWN";
    }

    public MarketCreatedEvent
decodeMarketCreated(
    Log receiptLog
) {

        Long marketId =
            Long.parseLong(
                receiptLog.getTopics()
                    .get(1)
                    .substring(2),
                16
            );

        log.debug(
    "MarketCreated raw data: {}",
    receiptLog.getData()
);

        String creator =
    "0x"
    + receiptLog.getTopics()
        .get(2)
        .substring(26);

        return MarketCreatedEvent
            .builder()
            .marketId(
                marketId
            )
            .question(
                new String(
                    org.web3j.utils.Numeric
                        .hexStringToByteArray(
                            receiptLog.getData()
    .substring(130)
                        )
                ).trim()
            )
            .creator(
                creator
            )
            .txHash(
                receiptLog.getTransactionHash()
            )
            .blockNumber(
                receiptLog.getBlockNumber()
                    .longValue()
            )
            .build();
    }

    public RewardClaimedEvent
decodeRewardClaimed(
    Log receiptLog
) {

    Long marketId =
        Long.parseLong(
            receiptLog.getTopics()
                .get(1)
                .substring(2),
            16
        );

    String trader =
        "0x"
        + receiptLog.getTopics()
            .get(2)
            .substring(26);

    Long amount =
        new java.math.BigInteger(
            receiptLog.getData()
                .substring(
                    2,
                    66
                ),
            16
        )
        .longValue();

    return RewardClaimedEvent
        .builder()
        .marketId(
            marketId
        )
        .trader(
            trader
        )
        .amount(
            amount
        )
        .txHash(
            receiptLog.getTransactionHash()
        )
        .blockNumber(
            receiptLog
                .getBlockNumber()
                .longValue()
        )
        .build();

}

    public SharesPurchasedEvent
    decodeSharesPurchased(
    Log receiptLog
) {

        String trader =
    "0x"
    + receiptLog.getTopics()
        .get(1)
        .substring(26);

        Long marketId =
    Long.parseLong(
        receiptLog.getTopics()
            .get(2)
            .substring(2),
        16
    );

        String data =
    receiptLog.getData();

        log.debug(
    "Trade raw data: {}",
    data
);

        log.debug(
    "Trade data length: {}",
    data.length()
);

        boolean side =
            new java.math.BigInteger(
                data.substring(
                    2,
                    66
                ),
                16
            )
            .equals(
                java.math.BigInteger.ONE
            );

        Long amount =
            new java.math.BigInteger(
                data.substring(
                    66,
                    130
                ),
                16
            )
            .longValue();

        return SharesPurchasedEvent
            .builder()
            .marketId(
                marketId
            )
            .trader(
                trader
            )
            .side(
                side
            )
            .amount(
                amount
            )
            .txHash(
    receiptLog.getTransactionHash()
)
            .blockNumber(
    receiptLog.getBlockNumber()
        .longValue()
)
            .build();
    }

    public MarketResolvedEvent
decodeMarketResolved(
    Log receiptLog
)
    {

        Long marketId =
            Long.parseLong(
                receiptLog.getTopics()
                    .get(1)
                    .substring(2),
                16
            );

        boolean outcome =
    new java.math.BigInteger(
        receiptLog.getData()
            .substring(
                2,
                66
            ),
        16
    )
            .equals(
                java.math.BigInteger.ONE
            );

        return MarketResolvedEvent
            .builder()
            .marketId(
                marketId
            )
            .outcome(
                outcome
            )
            .txHash(
    receiptLog.getTransactionHash()
)
            .blockNumber(
    receiptLog.getBlockNumber()
        .longValue()
)
            .build();
    }
}