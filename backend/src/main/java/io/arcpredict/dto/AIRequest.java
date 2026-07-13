package io.arcpredict.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AIRequest {

    @NotBlank(
        message = "Question is required"
    )
    private String question;

}