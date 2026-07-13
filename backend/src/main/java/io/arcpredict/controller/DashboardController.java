package io.arcpredict.controller;

import io.arcpredict.dto.DashboardResponse;
import io.arcpredict.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Dashboard",
    description = "Dashboard APIs"
)
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
    summary = "Get dashboard statistics",
    description = "Returns the aggregated dashboard metrics."
)
@GetMapping
   public DashboardResponse getDashboard()
    throws Exception {

        return dashboardService.getDashboard();

    }
}