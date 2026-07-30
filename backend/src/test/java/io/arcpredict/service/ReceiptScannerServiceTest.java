package io.arcpredict.service;

import io.arcpredict.repository.MarketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import io.arcpredict.dto.MarketCreatedEvent;
import io.arcpredict.dto.SharesPurchasedEvent;
import io.arcpredict.dto.MarketResolvedEvent;
import io.arcpredict.dto.RewardClaimedEvent;

import io.arcpredict.entity.MarketEntity;


@ExtendWith(MockitoExtension.class)
class ReceiptScannerServiceTest {

    @Mock
    private RpcClientService rpcClientService;

    @Mock
    private BlockchainDecoderService blockchainDecoderService;

    @Mock
    private MarketSyncService marketSyncService;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private MarketReaderService marketReaderService;

    @Mock
    private WebSocketBroadcastService webSocketBroadcastService;

    @InjectMocks
    private ReceiptScannerService receiptScannerService;

    @Test
    void shouldReturnWhenReceiptIsNull() throws Exception {

        when(
            rpcClientService.getTransactionReceipt(
                "0x123"
            )
        ).thenReturn(
            null
        );

        receiptScannerService.scanReceipt(
            "0x123"
        );

        verifyNoInteractions(
            blockchainDecoderService,
            marketSyncService,
            marketRepository,
            marketReaderService,
            webSocketBroadcastService
        );

    }

    @Test
    void shouldHandleReceiptWithNoLogs() throws Exception {

        TransactionReceipt receipt =
            mock(TransactionReceipt.class);

        when(
            rpcClientService.getTransactionReceipt(
                "0x123"
            )
        ).thenReturn(
            receipt
        );

        when(
            receipt.getLogs()
        ).thenReturn(
            java.util.Collections.emptyList()
        );

        receiptScannerService.scanReceipt(
            "0x123"
        );

        verifyNoInteractions(
            blockchainDecoderService,
            marketSyncService,
            marketRepository,
            marketReaderService,
            webSocketBroadcastService
        );

    }

    @Test
void shouldHandleExceptionWhenFetchingReceipt() throws Exception {

    when(
        rpcClientService.getTransactionReceipt(
            "0x123"
        )
    ).thenThrow(
        new RuntimeException("boom")
    );

    receiptScannerService.scanReceipt(
        "0x123"
    );

    verifyNoInteractions(
        blockchainDecoderService,
        marketSyncService,
        marketRepository,
        marketReaderService,
        webSocketBroadcastService
    );

}

@Test
void shouldIgnoreUnknownEventType() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0x123"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        java.util.Collections.emptyList()
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "UNKNOWN"
    );

    receiptScannerService.scanReceipt(
        "0x123"
    );

    verifyNoInteractions(
        marketSyncService,
        marketRepository,
        marketReaderService,
        webSocketBroadcastService
    );

}

@Test
void shouldProcessMarketCreatedEvent() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    MarketCreatedEvent event =
        mock(MarketCreatedEvent.class);

    MarketReaderService.MarketData marketData =
    mock(MarketReaderService.MarketData.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0x123"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        java.util.Collections.emptyList()
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "MARKET_CREATED"
    );

    when(
        blockchainDecoderService.decodeMarketCreated(
            log
        )
    ).thenReturn(
        event
    );

    when(
        event.getMarketId()
    ).thenReturn(
        1L
    );

    when(
        event.getQuestion()
    ).thenReturn(
        "Question?"
    );

    when(
        event.getCreator()
    ).thenReturn(
        "0xabc"
    );

    when(
        event.getTxHash()
    ).thenReturn(
        "0x123"
    );

    when(
    event.getBlockNumber()
).thenReturn(
    1L
);

    when(
        marketReaderService.getMarket(
            1L
        )
    ).thenReturn(
        marketData
    );

    when(
        marketData.category()
    ).thenReturn(
        "Sports"
    );

    when(
    marketData.endTime()
).thenReturn(
    123456789L
);

    receiptScannerService.scanReceipt(
        "0x123"
    );

    verify(
    marketSyncService
).saveMarket(
    any()
);

verify(
    marketSyncService
).saveEvent(
    eq("MARKET_CREATED"),
    eq(0L),
    eq("0x123"),
    eq(1L),
    any()
);

}

@Test
void shouldProcessSharesPurchasedEvent() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    SharesPurchasedEvent trade =
        mock(SharesPurchasedEvent.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0x456"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        java.util.Collections.emptyList()
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "SHARES_PURCHASED"
    );

    when(
        blockchainDecoderService.decodeSharesPurchased(
            log
        )
    ).thenReturn(
        trade
    );

    when(
        trade.getMarketId()
    ).thenReturn(
        10L
    );

    when(
        trade.getTrader()
    ).thenReturn(
        "0xabc"
    );

    when(
        trade.getSide()
    ).thenReturn(
        true
    );

    when(
        trade.getAmount()
    ).thenReturn(
        100L
    );

    when(
        trade.getTxHash()
    ).thenReturn(
        "0x456"
    );

    when(
        trade.getBlockNumber()
    ).thenReturn(
        20L
    );

    receiptScannerService.scanReceipt(
        "0x456"
    );

    verify(
    marketSyncService
).saveTrade(
    eq(10L),
    eq("0xabc"),
    eq(true),
    eq(100L),
    eq("0x456"),
    eq(20L)
);
    
}

@Test
void shouldProcessMarketResolvedEvent() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    MarketResolvedEvent event =
        mock(MarketResolvedEvent.class);

    MarketEntity market =
        mock(MarketEntity.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0x789"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        java.util.Collections.emptyList()
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "MARKET_RESOLVED"
    );

    when(
        blockchainDecoderService.decodeMarketResolved(
            log
        )
    ).thenReturn(
        event
    );

    when(
        event.getMarketId()
    ).thenReturn(
        5L
    );

    when(
        event.getOutcome()
    ).thenReturn(
        true
    );

    when(
        event.getTxHash()
    ).thenReturn(
        "0x789"
    );

    when(
        event.getBlockNumber()
    ).thenReturn(
        30L
    );

    when(
        marketRepository.findById(
            5L
        )
    ).thenReturn(
        Optional.of(market)
    );

    receiptScannerService.scanReceipt(
        "0x789"
    );

    verify(
    marketSyncService
).resolveMarket(
    eq(5L),
    eq(true)
);

verify(
    marketSyncService
).saveEvent(
    eq("MARKET_RESOLVED"),
    eq(5L),
    eq("0x789"),
    eq(30L),
    any()
);

}

@Test
void shouldProcessRewardClaimedEvent() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    RewardClaimedEvent event =
        mock(RewardClaimedEvent.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0x999"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        java.util.Collections.emptyList()
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "REWARD_CLAIMED"
    );

    when(
        blockchainDecoderService.decodeRewardClaimed(
            log
        )
    ).thenReturn(
        event
    );

    when(
        event.getMarketId()
    ).thenReturn(
        7L
    );

    when(
        event.getTrader()
    ).thenReturn(
        "0xabc"
    );

    when(
        event.getAmount()
    ).thenReturn(
        500L
    );

    when(
        event.getTxHash()
    ).thenReturn(
        "0x999"
    );

    when(
        event.getBlockNumber()
    ).thenReturn(
        40L
    );

    receiptScannerService.scanReceipt(
        "0x999"
    );

    verify(
    marketSyncService
).markRewardClaimed(
    eq(7L),
    eq("0xabc"),
    eq(500L)
);

verify(
    marketSyncService
).saveEvent(
    eq("REWARD_CLAIMED"),
    eq(7L),
    eq("0x999"),
    eq(40L),
    any()
);

}

@Test
void shouldHandleLogWithTopics() throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0xtopic"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        log.getTopics()
    ).thenReturn(
        List.of("0xtopic0")
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "UNKNOWN"
    );

    receiptScannerService.scanReceipt(
        "0xtopic"
    );

}

@Test
void shouldHandleMarketResolvedWhenMarketDoesNotExist()
    throws Exception {

    TransactionReceipt receipt =
        mock(TransactionReceipt.class);

    Log log =
        mock(Log.class);

    when(
        rpcClientService.getTransactionReceipt(
            "0xmissing"
        )
    ).thenReturn(
        receipt
    );

    when(
        receipt.getLogs()
    ).thenReturn(
        List.of(log)
    );

    when(
        blockchainDecoderService.eventType(
            log
        )
    ).thenReturn(
        "MARKET_RESOLVED"
    );


    MarketResolvedEvent event =
        MarketResolvedEvent.builder()
            .marketId(99L)
            .outcome(true)
            .txHash("0xmissing")
            .blockNumber(100L)
            .build();


    when(
        blockchainDecoderService.decodeMarketResolved(
            log
        )
    ).thenReturn(
        event
    );


    when(
        marketRepository.findById(
            99L
        )
    ).thenReturn(
        Optional.empty()
    );


    receiptScannerService.scanReceipt(
        "0xmissing"
    );


    verify(
        marketSyncService
    ).saveEvent(
        "MARKET_RESOLVED",
        99L,
        "0xmissing",
        100L,
        log.toString()
    );

}

}