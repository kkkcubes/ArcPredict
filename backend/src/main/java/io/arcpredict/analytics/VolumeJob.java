package io.arcpredict.analytics;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VolumeJob {

    private final AnalyticsService analyticsService;

    @Scheduled(fixedRate = 60000)
    public void execute() {

        AnalyticsEntity analytics =
                analyticsService.getAnalytics();

        System.out.println(
                "[VOLUME JOB] Total Volume: "
                        + analytics.getTotalVolume()
        );
    }
}