package io.arcpredict.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArcService {

    public Map<String, Object>
    getArcInfo() {

        Map<String, Object>
            response =
            new HashMap<>();

        response.put(
            "network",
            "Arc Testnet"
        );

        response.put(
            "rpc",
            "https://rpc.testnet.arc.network"
        );

        response.put(
            "chainId",
            5042002
        );

        response.put(
            "currency",
            "USDC"
        );

        response.put(
            "explorer",
            "https://testnet.arcscan.app"
        );

        return response;
    }
}