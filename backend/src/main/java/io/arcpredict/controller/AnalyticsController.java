package io.arcpredict.controller;

import io.arcpredict.dto.AnalyticsHistoryResponse;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.service.AnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Analytics",
    description = "Analytics and reporting APIs"
)
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(
        summary = "Get analytics",
        description = "Returns overall protocol analytics."
    )
    @GetMapping
    public AnalyticsEntity analytics() {

        return analyticsService.getAnalytics();

    }

    @Operation(
        summary = "Get analytics history",
        description = "Returns historical analytics for charts."
    )
    @GetMapping("/history")
    public AnalyticsHistoryResponse history() {

        return analyticsService.getAnalyticsHistory();

    }

}