package io.arcpredict.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

class WebSocketBroadcastServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private WebSocketBroadcastService service;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(
            this
        );

        service =
            new WebSocketBroadcastService(
                messagingTemplate
            );

    }

    @Test
    void shouldBroadcastMarket() {

        String payload = "market";

        service.broadcastMarket(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/markets",
            payload
        );

    }

    @Test
    void shouldBroadcastTrade() {

        String payload = "trade";

        service.broadcastTrade(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/trades",
            payload
        );

    }

    @Test
    void shouldBroadcastEvent() {

        String payload = "event";

        service.broadcastEvent(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/events",
            payload
        );

    }

    @Test
    void shouldBroadcastPortfolio() {

        String payload = "portfolio";

        service.broadcastPortfolio(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/portfolio",
            payload
        );

    }

    @Test
    void shouldBroadcastAnalytics() {

        String payload = "analytics";

        service.broadcastAnalytics(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/analytics",
            payload
        );

    }

    @Test
    void shouldBroadcastLeaderboard() {

        String payload = "leaderboard";

        service.broadcastLeaderboard(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/leaderboard",
            payload
        );

    }

    @Test
    void shouldBroadcastTransactionConfirmed() {

        String payload = "confirmed";

        service.broadcastTransactionConfirmed(
            payload
        );

        verify(
            messagingTemplate
        ).convertAndSend(
            "/topic/transaction-confirmed",
            payload
        );

    }

}