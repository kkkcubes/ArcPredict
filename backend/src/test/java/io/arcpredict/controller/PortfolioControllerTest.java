package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.service.PortfolioService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    PortfolioController.class
)

@Import(
    SecurityConfig.class
)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    void contextLoads() {

    }

}