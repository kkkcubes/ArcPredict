package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.service.ArcService;

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
    ArcController.class
)

@Import(
    SecurityConfig.class
)
class ArcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArcService arcService;

    @Test
    void shouldReturnArcInfo() throws Exception {

        Map<String, Object> response =

            new HashMap<>();

        response.put(
            "network",
            "Arc Testnet"
        );

        response.put(
            "chainId",
            11155111L
        );

        response.put(
            "rpcStatus",
            "Connected"
        );

        response.put(
            "latestBlock",
            123456L
        );

        when(
            arcService.getArcInfo()
        ).thenReturn(
            response
        );

        mockMvc.perform(
                get("/api/arc/info")
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
                    "$.chainId"
                ).value(
                    11155111
                )
            )
            .andExpect(
                jsonPath(
                    "$.rpcStatus"
                ).value(
                    "Connected"
                )
            )
            .andExpect(
                jsonPath(
                    "$.latestBlock"
                ).value(
                    123456
                )
            );

    }

}