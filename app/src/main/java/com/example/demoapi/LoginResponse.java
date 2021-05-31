package com.example.demoapi;

import org.jetbrains.annotations.NotNull;

public class LoginResponse{
    String refresh_token;
    String access_token;
    @NotNull
    public final String error_message;

    public LoginResponse(String refresh_token, String access_token, @NotNull String error_message) {
        this.refresh_token = refresh_token;
        this.access_token = access_token;
        this.error_message = error_message;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @NotNull
    public String getError_message() {
        return error_message;
    }
}