package io.arcpredict.controller;

import io.arcpredict.config.SecurityConfig;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    TradeController.class
)

@Import(
    SecurityConfig.class
)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    void contextLoads() {

    }

}