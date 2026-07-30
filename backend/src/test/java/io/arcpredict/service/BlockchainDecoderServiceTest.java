package io.arcpredict.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.web3j.protocol.core.methods.response.Log;

import java.util.Collections;
import java.util.List;
import java.math.BigInteger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.arcpredict.dto.MarketCreatedEvent;
import io.arcpredict.util.ContractEvents;

import io.arcpredict.dto.SharesPurchasedEvent;
import io.arcpredict.dto.MarketResolvedEvent;
import io.arcpredict.dto.RewardClaimedEvent;

class BlockchainDecoderServiceTest {

    private final BlockchainDecoderService
        blockchainDecoderService =
            new BlockchainDecoderService();

            @Test
void shouldReturnUnknownWhenTopicsAreEmpty() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        Collections.emptyList()
    );

    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );

    assertEquals(
        "UNKNOWN",
        eventType
    );

}

@Test
void shouldReturnMarketCreatedEventType() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        java.util.List.of(
            ContractEvents.MARKET_CREATED
        )
    );

    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );

    assertEquals(
        "MARKET_CREATED",
        eventType
    );

}

@Test
void shouldReturnSharesPurchasedEventType() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        java.util.List.of(
            ContractEvents.SHARES_PURCHASED
        )
    );

    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );

    assertEquals(
        "SHARES_PURCHASED",
        eventType
    );

}

@Test
void shouldReturnMarketResolvedEventType() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        java.util.List.of(
            ContractEvents.MARKET_RESOLVED
        )
    );

    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );

    assertEquals(
        "MARKET_RESOLVED",
        eventType
    );

}

@Test
void shouldReturnRewardClaimedEventType() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        java.util.List.of(
            ContractEvents.REWARD_CLAIMED
        )
    );

    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );

    assertEquals(
        "REWARD_CLAIMED",
        eventType
    );

}

@Test
void shouldDecodeMarketCreatedEvent() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        List.of(
            "ignored",
            "0x0000000000000000000000000000000000000000000000000000000000000001",
            "0x0000000000000000000000001111111111111111111111111111111111111111"
        )
    );

    when(
    receiptLog.getData()
).thenReturn(
    "0x"
    + "0000000000000000000000000000000000000000000000000000000000000000"
    + "0000000000000000000000000000000000000000000000000000000000000000"
    + "48656c6c6f"
);

    when(
        receiptLog.getTransactionHash()
    ).thenReturn(
        "0xabc"
    );

    when(
        receiptLog.getBlockNumber()
    ).thenReturn(
        BigInteger.valueOf(10)
    );

    MarketCreatedEvent event =
        blockchainDecoderService.decodeMarketCreated(
            receiptLog
        );

    assertEquals(
        1L,
        event.getMarketId()
    );

    assertEquals(
        "Hello",
        event.getQuestion()
    );

    assertEquals(
        "0x1111111111111111111111111111111111111111",
        event.getCreator()
    );

    assertEquals(
        "0xabc",
        event.getTxHash()
    );

    assertEquals(
        10L,
        event.getBlockNumber()
    );

}

@Test
void shouldDecodeSharesPurchasedEvent() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        List.of(
            "ignored",
            "0x0000000000000000000000002222222222222222222222222222222222222222",
            "0x000000000000000000000000000000000000000000000000000000000000000A"
        )
    );

    when(
        receiptLog.getData()
    ).thenReturn(
        "0x"
        + "0000000000000000000000000000000000000000000000000000000000000001"
        + "0000000000000000000000000000000000000000000000000000000000000064"
    );

    when(
        receiptLog.getTransactionHash()
    ).thenReturn(
        "0xtrade"
    );

    when(
        receiptLog.getBlockNumber()
    ).thenReturn(
        BigInteger.valueOf(20)
    );

    SharesPurchasedEvent event =
        blockchainDecoderService.decodeSharesPurchased(
            receiptLog
        );

    assertEquals(
        10L,
        event.getMarketId()
    );

    assertEquals(
        "0x2222222222222222222222222222222222222222",
        event.getTrader()
    );

    assertEquals(
        true,
        event.getSide()
    );

    assertEquals(
        100L,
        event.getAmount()
    );

    assertEquals(
        "0xtrade",
        event.getTxHash()
    );

    assertEquals(
        20L,
        event.getBlockNumber()
    );

}

@Test
void shouldDecodeMarketResolvedEvent() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        List.of(
            "ignored",
            "0x0000000000000000000000000000000000000000000000000000000000000005"
        )
    );

    when(
        receiptLog.getData()
    ).thenReturn(
        "0x"
        + "0000000000000000000000000000000000000000000000000000000000000001"
    );

    when(
        receiptLog.getTransactionHash()
    ).thenReturn(
        "0xresolved"
    );

    when(
        receiptLog.getBlockNumber()
    ).thenReturn(
        BigInteger.valueOf(30)
    );

    MarketResolvedEvent event =
        blockchainDecoderService.decodeMarketResolved(
            receiptLog
        );

    assertEquals(
        5L,
        event.getMarketId()
    );

    assertEquals(
        true,
        event.getOutcome()
    );

    assertEquals(
        "0xresolved",
        event.getTxHash()
    );

    assertEquals(
        30L,
        event.getBlockNumber()
    );

}

@Test
void shouldDecodeRewardClaimedEvent() {

    Log receiptLog =
        mock(Log.class);

    when(
        receiptLog.getTopics()
    ).thenReturn(
        List.of(
            "ignored",
            "0x0000000000000000000000000000000000000000000000000000000000000007",
            "0x0000000000000000000000003333333333333333333333333333333333333333"
        )
    );

    when(
        receiptLog.getData()
    ).thenReturn(
        "0x"
        + "00000000000000000000000000000000000000000000000000000000000001F4"
    );

    when(
        receiptLog.getTransactionHash()
    ).thenReturn(
        "0xreward"
    );

    when(
        receiptLog.getBlockNumber()
    ).thenReturn(
        BigInteger.valueOf(40)
    );

    RewardClaimedEvent event =
        blockchainDecoderService.decodeRewardClaimed(
            receiptLog
        );

    assertEquals(
        7L,
        event.getMarketId()
    );

    assertEquals(
        "0x3333333333333333333333333333333333333333",
        event.getTrader()
    );

    assertEquals(
        500L,
        event.getAmount()
    );

    assertEquals(
        "0xreward",
        event.getTxHash()
    );

    assertEquals(
        40L,
        event.getBlockNumber()
    );

}

@Test
void shouldReturnUnknownWhenTopicDoesNotMatchAnyEvent()
{

    Log receiptLog =
        mock(Log.class);


    when(
        receiptLog.getTopics()
    )
    .thenReturn(
        List.of(
            "0xunknown_topic"
        )
    );


    String eventType =
        blockchainDecoderService.eventType(
            receiptLog
        );


    assertEquals(
        "UNKNOWN",
        eventType
    );

}

@Test
void shouldDecodeSharesPurchasedNoSide()
{

    Log receiptLog =
        mock(Log.class);


    when(
        receiptLog.getTopics()
    )
    .thenReturn(
        List.of(
            "ignored",
            "0x0000000000000000000000002222222222222222222222222222222222222222",
            "0x000000000000000000000000000000000000000000000000000000000000000A"
        )
    );


    when(
        receiptLog.getData()
    )
    .thenReturn(
        "0x"
        + "0000000000000000000000000000000000000000000000000000000000000000"
        + "0000000000000000000000000000000000000000000000000000000000000064"
    );


    when(
        receiptLog.getTransactionHash()
    )
    .thenReturn(
        "0xtrade"
    );


    when(
        receiptLog.getBlockNumber()
    )
    .thenReturn(
        BigInteger.valueOf(20)
    );


    SharesPurchasedEvent event =
        blockchainDecoderService.decodeSharesPurchased(
            receiptLog
        );


    assertEquals(
        false,
        event.getSide()
    );


    assertEquals(
        100L,
        event.getAmount()
    );

}

}