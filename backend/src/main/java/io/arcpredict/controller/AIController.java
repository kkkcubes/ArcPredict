package io.arcpredict.controller;

import io.arcpredict.service.AnalyticsService;
import io.arcpredict.service.MarketService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final MarketService
        marketService;

    private final AnalyticsService
        analyticsService;

    @PostMapping("/ask")
    public Map<String, Object>
    ask(
        @RequestBody
        Map<String, String> request
    ) {

        String question =
            request.getOrDefault(
                "question",
                ""
            );

        Map<String, Object>
            response =
            new HashMap<>();

        if (
            question.toLowerCase()
                .contains("volume")
        ) {

            response.put(
                "answer",
                "Current volume: "
                +
                analyticsService
                    .getAnalytics()
                    .getTotalVolume()
            );

        } else if (

            question.toLowerCase()
                .contains("market")

        ) {

            response.put(
                "answer",
                "Total markets: "
                +
                marketService
                    .getMarkets()
                    .size()
            );

        } else {

            response.put(
                "answer",
                "ArcPredict AI is connected."
            );
        }

        return response;
    }
}