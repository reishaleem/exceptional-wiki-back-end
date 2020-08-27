package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class UpdateUserSecurityRequest {

    @NotBlank
    @Size(min = 6, max = 120)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 120)
    private String newPassword;
}