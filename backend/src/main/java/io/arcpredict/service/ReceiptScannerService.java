package io.arcpredict.service;

import io.arcpredict.dto.TransactionConfirmedMessage;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ReceiptScannerService {

    private static final Logger log =
        LoggerFactory.getLogger(
            ReceiptScannerService.class
        );

    private final RpcClientService rpcClientService;

    private final BlockchainDecoderService
        blockchainDecoderService;

    private final MarketSyncService
        marketSyncService;

    private final MarketRepository
        marketRepository;

    private final MarketReaderService
        marketReaderService;

    private final WebSocketBroadcastService
        webSocketBroadcastService;

    public void scanReceipt(
        String txHash
    ) {

        try {

            TransactionReceipt receipt =
    rpcClientService.getTransactionReceipt(
        txHash
    );

            if (
                receipt == null
            ) {
                return;
            }

            log.debug(
                "Receipt received: {}",
                txHash
            );

            log.info(
                "Receipt contains {} logs",
                receipt.getLogs().size()
            );

            for (
                int i = 0;
                i < receipt.getLogs().size();
                i++
            ) {

                Log receiptLog =
                    receipt.getLogs().get(i);

                log.info(
                    "Processing log {} of {}",
                    i + 1,
                    receipt.getLogs().size()
                );

                log.info(
                    "Log address: {}",
                    receiptLog.getAddress()
                );

                if (
                    !receiptLog.getTopics().isEmpty()
                ) {

                    log.debug(
                        "Topic0: {}",
                        receiptLog.getTopics().get(0)
                    );

                }

                log.debug(
                    "Expected MarketCreated topic: {}",
                    io.arcpredict.util.ContractEvents.MARKET_CREATED
                );

                String eventType =
                    blockchainDecoderService
                        .eventType(
                            receiptLog
                        );

                log.info(
                    "Detected event type: {}",
                    eventType
                );

                if (
                    "MARKET_CREATED"
                        .equals(eventType)
                ) {

                    var event =
                        blockchainDecoderService
                            .decodeMarketCreated(
                                receiptLog
                            );

                    log.debug(
                        "Market created tx: {}",
                        event.getTxHash()
                    );

                    log.debug(
                        "Market created block: {}",
                        event.getBlockNumber()
                    );

                    log.debug(
                        "Market id: {}",
                        event.getMarketId()
                    );

                    log.debug(
                        "Creator: {}",
                        event.getCreator()
                    );

                    log.debug(
                        "Question: {}",
                        event.getQuestion()
                    );

                    log.debug(
                        "Question length: {}",
                        event.getQuestion().length()
                    );

                    var marketData =
                        marketReaderService.getMarket(
                            event.getMarketId()
                        );

                        log.info(
    "MarketData category: {}",
    marketData.category()
);

log.info(
    "MarketData endTime: {}",
    marketData.endTime()
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
                            .protocolFees(
                                0L
                            )
                            .participants(
                                0L
                            )
                            .blockNumber(
                                event.getBlockNumber()
                            )
                            .createdAt(
                                Instant.now()
                            )
                            .updatedAt(
                                Instant.now()
                            )
                            .build();

                    marketSyncService.saveMarket(
                        market
                    );

                    log.debug(
                        "Market exists: {}",
                        marketRepository.existsById(
                            event.getMarketId()
                        )
                    );

                    log.info(
                        "Market saved"
                    );

                    marketSyncService.saveEvent(
                        "MARKET_CREATED",
                        0L,
                        event.getTxHash(),
                        event.getBlockNumber(),
                        receiptLog.toString()
                    );

                    log.info(
                        "Market creation event saved"
                    );

                }

                if (
                    "SHARES_PURCHASED"
                        .equals(eventType)
                ) {

                    var trade =
                        blockchainDecoderService
                            .decodeSharesPurchased(
                                receiptLog
                            );

                    log.debug(
                        "Trade market id: {}",
                        trade.getMarketId()
                    );

                    log.debug(
                        "Trader: {}",
                        trade.getTrader()
                    );

                    log.debug(
                        "Side: {}",
                        trade.getSide()
                    );

                    log.debug(
                        "Amount: {}",
                        trade.getAmount()
                    );

                    marketSyncService.saveTrade(
                        trade.getMarketId(),
                        trade.getTrader(),
                        trade.getSide(),
                        trade.getAmount(),
                        trade.getTxHash(),
                        trade.getBlockNumber()
                    );

                    marketSyncService.saveEvent(
                        "SHARES_PURCHASED",
                        trade.getMarketId(),
                        trade.getTxHash(),
                        trade.getBlockNumber(),
                        receiptLog.toString()
                    );

                    TransactionConfirmedMessage message =
                        TransactionConfirmedMessage
                            .builder()
                            .type(
                                "TRANSACTION_CONFIRMED"
                            )
                            .txHash(
                                trade.getTxHash()
                            )
                            .marketId(
                                trade.getMarketId()
                            )
                            .build();

                    webSocketBroadcastService
                        .broadcastTransactionConfirmed(
                            message
                        );

                    log.info(
                        "Trade saved"
                    );

                }

                if (
                    "MARKET_RESOLVED"
                        .equals(eventType)
                ) {

                    var event =
                        blockchainDecoderService
                            .decodeMarketResolved(
                                receiptLog
                            );

                    log.debug(
                        "Resolved market id: {}",
                        event.getMarketId()
                    );

                    log.debug(
                        "Outcome: {}",
                        event.getOutcome()
                    );

                    var market =
                        marketRepository
                            .findById(
                                event.getMarketId()
                            )
                            .orElse(null);

                    log.debug(
                        "Market found: {}",
                        market != null
                    );

                    if (
                        market != null
                    ) {

                        market.setResolved(
                            true
                        );

                        market.setOutcome(
                            event.getOutcome()
                        );

                        marketSyncService.saveMarket(
                            market
                        );

                        marketSyncService.resolveMarket(
                            event.getMarketId(),
                            event.getOutcome()
                        );

                        log.info(
                            "Market updated"
                        );

                    }

                    marketSyncService.saveEvent(
                        "MARKET_RESOLVED",
                        event.getMarketId(),
                        event.getTxHash(),
                        event.getBlockNumber(),
                        receiptLog.toString()
                    );

                    log.info(
                        "Resolution event saved"
                    );

                }

                if (
                    "REWARD_CLAIMED"
                        .equals(eventType)
                ) {

                    var event =
                        blockchainDecoderService
                            .decodeRewardClaimed(
                                receiptLog
                            );

                    marketSyncService
                        .markRewardClaimed(
                            event.getMarketId(),
                            event.getTrader(),
                            event.getAmount()
                        );

                    marketSyncService.saveEvent(
                        "REWARD_CLAIMED",
                        event.getMarketId(),
                        event.getTxHash(),
                        event.getBlockNumber(),
                        receiptLog.toString()
                    );

                    log.info(
                        "Reward claimed"
                    );

                }

            }

        } catch (Exception e) {

            log.error(
                "Failed to scan receipt {}",
                txHash,
                e
            );

        }

    }

}