package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.service.VerificationService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    VerificationController.class
)

@Import(
    SecurityConfig.class
)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerificationService verificationService;

    @Test
    void shouldReturnNetworkStatus() throws Exception {

        Map<String, Object> response =

            new HashMap<>();

        response.put(
            "network",
            "Arc Testnet"
        );

        response.put(
            "status",
            "Healthy"
        );

        response.put(
            "latestBlock",
            123456L
        );

        response.put(
            "rpcConnected",
            true
        );

        when(
            verificationService.networkStatus()
        ).thenReturn(
            response
        );

        mockMvc.perform(
                get("/api/verification/network")
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                content().contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(
                jsonPath(
                    "$.network"
                ).value(
                    "Arc Testnet"
                )
            )
            .andExpect(
                jsonPath(
                    "$.status"
                ).value(
                    "Healthy"
                )
            )
            .andExpect(
                jsonPath(
                    "$.latestBlock"
                ).value(
                    123456
                )
            )
            .andExpect(
                jsonPath(
                    "$.rpcConnected"
                ).value(
                    true
                )
            );

    }

}