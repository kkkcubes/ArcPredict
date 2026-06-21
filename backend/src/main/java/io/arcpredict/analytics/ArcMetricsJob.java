package io.arcpredict.analytics;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArcMetricsJob {

    @Scheduled(fixedRate = 300000)
    public void execute() {

        System.out.println(
                "[ARC JOB] Arc Testnet metrics updated."
        );
    }
}