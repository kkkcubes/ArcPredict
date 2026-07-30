package io.arcpredict.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

class MetricsServiceTest {

    private final SimpleMeterRegistry meterRegistry =
        new SimpleMeterRegistry();

    private final MetricsService metricsService =
        new MetricsService(
            meterRegistry
        );

        @Test
void shouldRegisterMetrics() {

    metricsService.init();

    assertEquals(
        1,
        meterRegistry
            .getMeters()
            .size()
            >= 7 ? 1 : 0
    );

}

@Test
void shouldUpdateLatestBlockchainBlockGauge() {

    metricsService.init();

    metricsService.setLatestBlockchainBlock(
        123L
    );

    double value =
        meterRegistry
            .get(
                "arcpredict.blockchain.latest_block"
            )
            .gauge()
            .value();

    assertEquals(
        123.0,
        value
    );

}

@Test
void shouldUpdateRpcLatencyGauge() {

    metricsService.init();

    metricsService.setRpcLatency(
        250L
    );

    double value =
        meterRegistry
            .get(
                "arcpredict.rpc.latency"
            )
            .gauge()
            .value();

    assertEquals(
        250.0,
        value
    );

}

@Test
void shouldIncrementMarketsProcessedCounter() {

    metricsService.init();

    metricsService.incrementMarketsProcessed();

    double count =
        meterRegistry
            .get(
                "arcpredict.market.processed"
            )
            .counter()
            .count();

    assertEquals(
        1.0,
        count
    );

}

@Test
void shouldIncrementTradesProcessedCounter() {

    metricsService.init();

    metricsService.incrementTradesProcessed();

    double count =
        meterRegistry
            .get(
                "arcpredict.trade.processed"
            )
            .counter()
            .count();

    assertEquals(
        1.0,
        count
    );

}

@Test
void shouldIncrementReceiptFailuresCounter() {

    metricsService.init();

    metricsService.incrementReceiptFailures();

    double count =
        meterRegistry
            .get(
                "arcpredict.receipt.failures"
            )
            .counter()
            .count();

    assertEquals(
        1.0,
        count
    );

}

@Test
void shouldUpdateLastProcessedBlockGauge() {

    metricsService.init();

    metricsService.setLastProcessedBlock(
        456L
    );

    double value =
        meterRegistry
            .get(
                "arcpredict.blockchain.last_processed_block"
            )
            .gauge()
            .value();

    assertEquals(
        456.0,
        value
    );

}

@Test
void shouldUpdateWebSocketConnectionsGauge() {

    metricsService.init();

    metricsService.setWebSocketConnections(
        25L
    );

    double value =
        meterRegistry
            .get(
                "arcpredict.websocket.connections"
            )
            .gauge()
            .value();

    assertEquals(
        25.0,
        value
    );

}

}