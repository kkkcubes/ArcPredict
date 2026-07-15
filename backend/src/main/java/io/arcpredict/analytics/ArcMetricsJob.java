package io.arcpredict.analytics;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArcMetricsJob {

    private static final Logger log =
        LoggerFactory.getLogger(
            ArcMetricsJob.class
        );

    @Scheduled(fixedRate = 300000)
    public void execute() {

        log.info(
            "Arc Testnet metrics updated."
        );

    }

}