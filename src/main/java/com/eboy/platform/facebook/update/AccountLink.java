package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountLink {

    @JsonProperty
    private String status;

    @JsonProperty("authorization_code")
    private String authorizationCode;

    public AccountLink() {
    }

    public AccountLink(String status, String authorizationCode) {
        this.status = status;
        this.authorizationCode = authorizationCode;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    @Override
    public String toString() {
        return "AccountLink{" +
                "status='" + status + '\'' +
                ", authorizationCode='" + authorizationCode + '\'' +
                '}';
    }
}
