package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.service.AnalyticsService;
import io.arcpredict.service.MarketService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    AIController.class
)

@Import(
    SecurityConfig.class
)
class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketService marketService;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void contextLoads() {

    }

}