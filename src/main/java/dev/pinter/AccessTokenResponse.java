/*
 * Copyright (C) 2021 - Flavio Freitas Pinter
 */

package dev.pinter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    public AccessTokenResponse() {
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "\naccess_token='" + accessToken + "\n" +
                ", token_type='" + tokenType + "\n" +
                ", expires_in=" + expiresIn +
                '}';
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
