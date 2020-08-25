package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.*;

public class UpdateUserSecurityRequest {

    @NotBlank
    private String id;

    @NotBlank
    @Size(min = 6, max = 120)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 120)
    private String newPassword;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String password) {
        this.newPassword = password;
    }
}