package io.arcpredict.controller;

import io.arcpredict.dto.ActivityResponse;
import io.arcpredict.service.EventService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService
        eventService;

    @GetMapping
    public List<ActivityResponse> getActivityFeed() {

        return eventService
            .getActivityFeed();

    }

}