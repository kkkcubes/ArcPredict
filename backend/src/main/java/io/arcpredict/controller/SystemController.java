package io.arcpredict.controller;

import io.arcpredict.dto.InfrastructureMetricsResponse;
import io.arcpredict.service.SystemService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;

    @GetMapping("/metrics")
    public InfrastructureMetricsResponse getMetrics() {

        return systemService.getMetrics();

    }

}