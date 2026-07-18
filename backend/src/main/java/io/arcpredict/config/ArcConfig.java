package io.arcpredict.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
@RequiredArgsConstructor
public class ArcConfig {

    private final ArcRpcProperties arcRpcProperties;

    @Bean
    public OkHttpClient okHttpClient() {

        return new OkHttpClient.Builder()

            .connectTimeout(
                10,
                TimeUnit.SECONDS
            )

            .readTimeout(
                30,
                TimeUnit.SECONDS
            )

            .writeTimeout(
                30,
                TimeUnit.SECONDS
            )

            .retryOnConnectionFailure(
                true
            )

            .build();

    }

    @Bean
    public Web3j web3j(
        OkHttpClient okHttpClient
    ) {

        String rpcUrl =
            arcRpcProperties
                .getUrls()
                .get(0);

        return Web3j.build(
            new HttpService(
                rpcUrl,
                okHttpClient
            )
        );

    }

}