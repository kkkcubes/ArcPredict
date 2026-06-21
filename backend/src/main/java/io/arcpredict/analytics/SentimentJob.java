package io.arcpredict.analytics;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SentimentJob {

    private final AnalyticsService analyticsService;

    @Scheduled(fixedRate = 120000)
    public void execute() {

        AnalyticsEntity analytics =
                analyticsService.getAnalytics();

        System.out.println(
                "[SENTIMENT JOB] Bullish: "
                        + analytics.getBullishPercentage()
                        + "%"
        );
    }
}