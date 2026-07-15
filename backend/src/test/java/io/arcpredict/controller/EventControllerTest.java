package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.dto.ActivityResponse;
import io.arcpredict.service.EventService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    EventController.class
)

@Import(
    SecurityConfig.class
)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void shouldReturnActivityFeed() throws Exception {

        List<ActivityResponse> activities = List.of(

            ActivityResponse.builder()
                .id(1L)
                .eventType("TRADE")
                .marketId(1L)
                .wallet("0xabc")
                .amount(100L)
                .position("YES")
                .txHash("0x111")
                .blockNumber(12345L)
                .timestamp(
                    Instant.parse(
                        "2026-07-15T09:00:00Z"
                    )
                )
                .summary(
                    "0xabc bought YES shares"
                )
                .build()

        );

        when(
            eventService.getActivityFeed()
        ).thenReturn(
            activities
        );

        mockMvc.perform(
                get("/api/events")
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
                    "$[0].eventType"
                ).value(
                    "TRADE"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].marketId"
                ).value(
                    1
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].wallet"
                ).value(
                    "0xabc"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].amount"
                ).value(
                    100
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].position"
                ).value(
                    "YES"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].txHash"
                ).value(
                    "0x111"
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].blockNumber"
                ).value(
                    12345
                )
            )
            .andExpect(
                jsonPath(
                    "$[0].summary"
                ).value(
                    "0xabc bought YES shares"
                )
            );

    }

}