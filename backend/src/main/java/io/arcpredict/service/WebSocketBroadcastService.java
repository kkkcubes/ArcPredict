package io.arcpredict.service;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketBroadcastService {

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

    System.out.println(
        "===================================="
    );

    System.out.println(
        "Broadcasting portfolio..."
    );

    System.out.println(
        payload
    );

    System.out.println(
        "Destination: /topic/portfolio"
    );

    System.out.println(
        "===================================="
    );

    messagingTemplate.convertAndSend(
        "/topic/portfolio",
        payload
    );

    System.out.println(
        "Portfolio broadcast finished"
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