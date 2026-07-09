package io.arcpredict.controller;

import io.arcpredict.dto.RpcHealthResponse;
import io.arcpredict.service.RpcHealthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class RpcHealthController {

    private final RpcHealthService rpcHealthService;

    @GetMapping("/rpc")
    public RpcHealthResponse getRpcHealth() {

        return rpcHealthService.getRpcHealth();

    }
}