package com.hand.sxy.system.dto;

import java.io.Serializable;

public class TokenResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
