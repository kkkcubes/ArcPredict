package io.arcpredict.service;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ReceiptScannerService {

    private final Web3j web3j;

    private final BlockchainDecoderService
        blockchainDecoderService;

    private final MarketSyncService
        marketSyncService;

    private final MarketRepository
        marketRepository;

    private final TradeRepository
        tradeRepository;

    private final MarketReaderService
    marketReaderService;    

    public void scanReceipt(
        String txHash
    ) {

        try {

            TransactionReceipt receipt =
                web3j
                    .ethGetTransactionReceipt(
                        txHash
                    )
                    .send()
                    .getTransactionReceipt()
                    .orElse(null);

            if (
                receipt == null
            ) {
                return;
            }

            System.out.println(
                "RECEIPT => "
                + txHash
            );

            for (
                Log log :
                receipt.getLogs()
            ) {

                System.out.println(
                    "LOG ADDRESS => "
                    + log.getAddress()
                );

                System.out.println(
                    "TOPIC0 => "
                    + log.getTopics().get(0)
                );

                System.out.println(
                    "MARKET_CREATED => "
                    + io.arcpredict.util.ContractEvents.MARKET_CREATED
                );

                String eventType =
                    blockchainDecoderService
                        .eventType(log);

                System.out.println(
                    "EVENT TYPE => "
                    + eventType
                );

                if (
                    "MARKET_CREATED"
                        .equals(eventType)
                ) {

                    var event =
                        blockchainDecoderService
                            .decodeMarketCreated(
                                log
                            );

                    System.out.println(
                        "MARKET CREATED TX => "
                        + event.getTxHash()
                    );

                    System.out.println(
                        "MARKET CREATED BLOCK => "
                        + event.getBlockNumber()
                    );

                    System.out.println(
                        "MARKET ID => "
                        + event.getMarketId()
                    );

                    System.out.println(
                        "CREATOR => "
                        + event.getCreator()
                    );

                    System.out.println(
                        "QUESTION => "
                        + event.getQuestion()
                    );

                    System.out.println(
                        "QUESTION LENGTH => "
                        + event.getQuestion().length()
                    );

                    var marketData =
    marketReaderService.getMarket(
        event.getMarketId()
    );

MarketEntity market =
    MarketEntity.builder()
        .marketId(
            event.getMarketId()
        )
        .question(
            event.getQuestion()
        )
        .creator(
            event.getCreator()
        )
        .category(
            marketData.category()
        )
        .endTime(
            marketData.endTime()
        )
        .yesPool(
            0L
        )
        .noPool(
            0L
        )
        .resolved(
            false
        )
        .totalVolume(
            0L
        )
        .participants(
            0L
        )
        .createdAt(
            Instant.now()
        )
        .build();

                    marketSyncService.saveMarket(
                        market
                    );

                    System.out.println(
                        "MARKET EXISTS => "
                        + marketRepository.existsById(
                            event.getMarketId()
                        )
                    );

                    System.out.println(
                        "MARKET SAVED"
                    );

                    marketSyncService.saveEvent(
                        "MARKET_CREATED",
                        0L,
                        event.getTxHash(),
                        event.getBlockNumber(),
                        log.toString()
                    );

                    System.out.println(
                        "EVENT SAVED"
                    );
                }

                if (
                    "SHARES_PURCHASED"
                        .equals(eventType)
                )
                {

                    var trade =
                        blockchainDecoderService
                            .decodeSharesPurchased(
                                log
                            );

                    System.out.println(
                        "TRADE MARKET ID => "
                        + trade.getMarketId()
                    );

                    System.out.println(
                        "TRADER => "
                        + trade.getTrader()
                    );

                    System.out.println(
                        "SIDE => "
                        + trade.getSide()
                    );

                    System.out.println(
                        "AMOUNT => "
                        + trade.getAmount()
                    );

                    marketSyncService.saveTrade(
                        trade.getMarketId(),
                        trade.getTrader(),
                        trade.getSide(),
                        trade.getAmount(),
                        trade.getTxHash(),
                        trade.getBlockNumber()
                    );

                    System.out.println(
                        "TRADE SAVED"
                    );

                    var market =
                        marketRepository
                            .findById(
                                trade.getMarketId()
                            )
                            .orElse(null);

                    System.out.println(
                        "MARKET FOUND => "
                        + (market != null)
                    );

                    if (
                        market != null
                    )
                    {

                        if (
                            trade.getSide()
                        )
                        {

                            market.setYesPool(
                                market.getYesPool()
                                + trade.getAmount()
                            );
                        }
                        else
                        {

                            market.setNoPool(
                                market.getNoPool()
                                + trade.getAmount()
                            );
                        }

                        market.setTotalVolume(
                            market.getTotalVolume()
                            + trade.getAmount()
                        );

                        long participantCount =
    tradeRepository
        .countDistinctTraderByMarketId(
            trade.getMarketId()
        );

market.setParticipants(
    participantCount
);

                        marketSyncService.saveMarket(
                            market
                        );

                        System.out.println(
                            "MARKET STATS UPDATED"
                        );

                        System.out.println(
                            "YES POOL => "
                            + market.getYesPool()
                        );

                        System.out.println(
                            "NO POOL => "
                            + market.getNoPool()
                        );

                        System.out.println(
                            "TOTAL VOLUME => "
                            + market.getTotalVolume()
                        );

                        System.out.println(
                            "PARTICIPANTS => "
                            + market.getParticipants()
                        );
                    }
                }

                if (
                    "MARKET_RESOLVED"
                        .equals(eventType)
                )
                {

                    var event =
                        blockchainDecoderService
                            .decodeMarketResolved(
                                log
                            );

                    System.out.println(
                        "RESOLVED MARKET ID => "
                        + event.getMarketId()
                    );

                    System.out.println(
                        "OUTCOME => "
                        + event.getOutcome()
                    );

                    var market =
                        marketRepository
                            .findById(
                                event.getMarketId()
                            )
                            .orElse(null);

                    System.out.println(
                        "MARKET FOUND => "
                        + (market != null)
                    );

                    if (
                        market != null
                    )
                    {

                        market.setResolved(
                            true
                        );

                        market.setOutcome(
                            event.getOutcome()
                        );

                        marketSyncService
                            .saveMarket(
                                market
                            );

                        System.out.println(
                            "MARKET UPDATED"
                        );
                    }

                    marketSyncService.saveEvent(
                        "MARKET_RESOLVED",
                        event.getMarketId(),
                        event.getTxHash(),
                        event.getBlockNumber(),
                        log.toString()
                    );

                    System.out.println(
                        "RESOLUTION SAVED"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
