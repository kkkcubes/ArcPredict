package io.arcpredict.controller;

import io.arcpredict.dto.MarketSentimentResponse;
import io.arcpredict.service.MarketSentimentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/markets")
@RequiredArgsConstructor
public class MarketSentimentController {

    private final MarketSentimentService
        marketSentimentService;

    @GetMapping("/sentiment")
    public List<MarketSentimentResponse>
    getMarketSentiment() {

        return marketSentimentService
            .getMarketSentiment();

    }
}