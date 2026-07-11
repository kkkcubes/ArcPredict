package io.arcpredict.controller;

import io.arcpredict.dto.AnalyticsHistoryResponse;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService
        analyticsService;

    @GetMapping
    public AnalyticsEntity
    analytics() {

        return analyticsService
            .getAnalytics();
    }
    @GetMapping("/history")
public AnalyticsHistoryResponse
history() {

    return analyticsService
        .getAnalyticsHistory();

}
}