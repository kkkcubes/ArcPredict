package io.arcpredict.service;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArcServiceTest {

    private final ArcService arcService =
        new ArcService();

    @Test
    void shouldReturnArcInfo() {

        Map<String, Object> info =
            arcService.getArcInfo();

        assertEquals(
            "Arc Testnet",
            info.get("network")
        );

        assertEquals(
            "https://rpc.testnet.arc.network",
            info.get("rpc")
        );

        assertEquals(
            5042002,
            info.get("chainId")
        );

        assertEquals(
            "USDC",
            info.get("currency")
        );

        assertEquals(
            "https://testnet.arcscan.app",
            info.get("explorer")
        );

    }

}