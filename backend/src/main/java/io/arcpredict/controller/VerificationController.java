package io.arcpredict.controller;

import io.arcpredict.service.VerificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService
        verificationService;

    @GetMapping("/network")
    public Map<String, Object>
    networkStatus() {

        return verificationService
            .networkStatus();
    }
}