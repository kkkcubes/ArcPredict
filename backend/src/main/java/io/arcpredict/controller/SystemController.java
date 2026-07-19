package io.arcpredict.controller;

import io.arcpredict.dto.InfrastructureMetricsResponse;
import io.arcpredict.dto.SystemHealthResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.arcpredict.service.SystemService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "System",
    description = "System monitoring APIs"
)
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;

    @Operation(
        summary = "Get infrastructure metrics",
        description = "Returns RPC, database and infrastructure metrics."
    )
    @GetMapping("/metrics")
    public InfrastructureMetricsResponse getMetrics() {

        return systemService.getMetrics();

    }

    @Operation(
        summary = "Get system health",
        description = "Returns the current health status of the backend."
    )
    @GetMapping("/health")
    public SystemHealthResponse getHealth() {

        return systemService.getHealth();

    }

}