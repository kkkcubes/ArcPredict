package io.arcpredict.controller;

import io.arcpredict.service.VerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
    name = "Verification",
    description = "Smart contract verification APIs"
)
@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService
        verificationService;

        @Operation(
    summary = "Get verification status",
    description = "Returns smart contract verification information."
)

    @GetMapping("/network")
    public Map<String, Object>
    networkStatus() {

        return verificationService
            .networkStatus();
    }
}