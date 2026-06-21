package io.arcpredict.controller;

import io.arcpredict.service.ArcService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/arc")
@RequiredArgsConstructor
public class ArcController {

    private final ArcService
        arcService;

    @GetMapping("/info")
    public Map<String, Object>
    info() {

        return arcService
            .getArcInfo();
    }
}