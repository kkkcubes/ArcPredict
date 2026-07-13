package io.arcpredict.controller;

import io.arcpredict.dto.AIRequest;
import io.arcpredict.service.AnalyticsService;
import io.arcpredict.service.MarketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(
    name = "AI",
    description = "AI assistant APIs"
)

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final MarketService
        marketService;

    private final AnalyticsService
        analyticsService;

        @Operation(
    summary = "Ask AI",
    description = "Returns an AI-generated answer for the submitted question."
)

    @PostMapping("/ask")
    public Map<String, Object>
    ask(

        @Valid
        @RequestBody
        AIRequest request

    ) {

        String question =
            request.getQuestion();

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