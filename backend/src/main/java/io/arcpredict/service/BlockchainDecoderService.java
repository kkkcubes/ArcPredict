package io.arcpredict.service;

import io.arcpredict.dto.MarketCreatedEvent;
import io.arcpredict.dto.MarketResolvedEvent;
import io.arcpredict.dto.SharesPurchasedEvent;

import io.arcpredict.util.ContractEvents;

import org.springframework.stereotype.Service;

import org.web3j.protocol.core.methods.response.Log;

@Service
public class BlockchainDecoderService {

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

        return "UNKNOWN";
    }

    public MarketCreatedEvent
    decodeMarketCreated(
        Log log
    ) {

        Long marketId =
            Long.parseLong(
                log.getTopics()
                    .get(1)
                    .substring(2),
                16
            );

        System.out.println(
            "RAW DATA => "
            + log.getData()
        );

        String creator =
            "0x"
            + log.getTopics()
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
                            log.getData()
                                .substring(130)
                        )
                ).trim()
            )
            .creator(
                creator
            )
            .txHash(
                log.getTransactionHash()
            )
            .blockNumber(
                log.getBlockNumber()
                    .longValue()
            )
            .build();
    }

    public SharesPurchasedEvent
    decodeSharesPurchased(
        Log log
    ) {

        String trader =
            "0x"
            + log.getTopics()
                .get(1)
                .substring(26);

        Long marketId =
            Long.parseLong(
                log.getTopics()
                    .get(2)
                    .substring(2),
                16
            );

        String data =
            log.getData();

        System.out.println(
            "TRADE RAW DATA => "
            + data
        );

        System.out.println(
            "TRADE DATA LENGTH => "
            + data.length()
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
                log.getTransactionHash()
            )
            .blockNumber(
                log.getBlockNumber()
                    .longValue()
            )
            .build();
    }

    public MarketResolvedEvent
    decodeMarketResolved(
        Log log
    )
    {

        Long marketId =
            Long.parseLong(
                log.getTopics()
                    .get(1)
                    .substring(2),
                16
            );

        boolean outcome =
            new java.math.BigInteger(
                log.getData()
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
                log.getTransactionHash()
            )
            .blockNumber(
                log.getBlockNumber()
                    .longValue()
            )
            .build();
    }
}