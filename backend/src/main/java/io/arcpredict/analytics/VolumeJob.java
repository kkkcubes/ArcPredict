package io.arcpredict.analytics;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VolumeJob {

    private static final Logger log =
        LoggerFactory.getLogger(
            VolumeJob.class
        );

    private final AnalyticsService analyticsService;

    @Scheduled(fixedRate = 60000)
    public void execute() {

        AnalyticsEntity analytics =
            analyticsService.getAnalytics();

        log.info(
            "Total Volume: {}",
            analytics.getTotalVolume()
        );

    }

}