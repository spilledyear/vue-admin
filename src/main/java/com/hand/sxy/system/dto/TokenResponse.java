package com.hand.sxy.system.dto;

import java.io.Serializable;

/**
 * @author spilledyear
 */
public class TokenResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    private final boolean success;

    public TokenResponse(boolean success, String token) {
        this.token = token;
        this.success = success;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
