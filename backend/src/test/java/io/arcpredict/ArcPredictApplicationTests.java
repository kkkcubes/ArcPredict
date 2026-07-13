package io.arcpredict;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {

        "contracts.vault-address=0x1111111111111111111111111111111111111111",

        "contracts.treasury-address=0x2222222222222222222222222222222222222222",

        "contracts.reward-distributor-address=0x3333333333333333333333333333333333333333",

        "contracts.market-factory-address=0x4444444444444444444444444444444444444444",

        "contracts.prediction-market-address=0x5555555555555555555555555555555555555555"

    }
)
class ArcPredictApplicationTests {

    @Test
    void contextLoads() {

    }

}