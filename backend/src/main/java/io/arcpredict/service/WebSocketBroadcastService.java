package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WebSocketBroadcastService {

    private static final Logger log =
    LoggerFactory.getLogger(
        WebSocketBroadcastService.class
    );

    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastMarket(
        Object payload
    ) {

        messagingTemplate.convertAndSend(
            "/topic/markets",
            payload
        );
    }

    public void broadcastTrade(
        Object payload
    ) {

        messagingTemplate.convertAndSend(
            "/topic/trades",
            payload
        );
    }

    public void broadcastEvent(
        Object payload
    ) {

        messagingTemplate.convertAndSend(
            "/topic/events",
            payload
        );
    }

    public void broadcastPortfolio(
    Object payload
) {

    log.info(
        "Broadcasting portfolio to /topic/portfolio"
    );

    log.debug(
        "Portfolio payload: {}",
        payload
    );

    messagingTemplate.convertAndSend(
        "/topic/portfolio",
        payload
    );

    log.info(
        "Portfolio broadcast completed"
    );

}

    public void broadcastAnalytics(
        Object payload
    ) {

        messagingTemplate.convertAndSend(
            "/topic/analytics",
            payload
        );
    }

    public void broadcastLeaderboard(
        Object payload
    ) {

        messagingTemplate.convertAndSend(
            "/topic/leaderboard",
            payload
        );
    }
    public void broadcastTransactionConfirmed(
    Object payload
) {

    messagingTemplate.convertAndSend(
        "/topic/transaction-confirmed",
        payload
    );

}
}