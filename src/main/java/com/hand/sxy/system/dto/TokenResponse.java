package com.hand.sxy.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * @author spilledyear
 */
public class TokenResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final boolean success;

    private final String token;

    private final Long code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> rows;


    public TokenResponse(boolean success, Long code, String token) {
        this.token = token;
        this.code = code;
        this.success = success;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
