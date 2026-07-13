package io.arcpredict.controller;

import io.arcpredict.dto.ActivityResponse;
import io.arcpredict.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
    name = "Events",
    description = "Activity feed APIs"
)

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService
        eventService;

        @Operation(
    summary = "Get activity feed",
    description = "Returns the latest blockchain activity feed."
)

    @GetMapping
    public List<ActivityResponse> getActivityFeed() {

        return eventService
            .getActivityFeed();

    }

}