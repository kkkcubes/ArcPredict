package io.arcpredict.config;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "contracts")
public class ContractProperties {

    @NotBlank
    private String vaultAddress;

    @NotBlank
    private String treasuryAddress;

    @NotBlank
    private String rewardDistributorAddress;

    @NotBlank
    private String marketFactoryAddress;

    @NotBlank
    private String predictionMarketAddress;

}