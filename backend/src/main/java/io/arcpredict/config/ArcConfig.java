package io.arcpredict.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class ArcConfig {

    @Value("${arc.rpc.url}")
    private String rpcUrl;

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

        return Web3j.build(
            new HttpService(
                rpcUrl,
                okHttpClient
            )
        );

    }

}