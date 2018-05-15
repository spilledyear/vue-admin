package com.hand.sxy.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/24 20:27
 */
public class ResultResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> rows;

    private Long total;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private boolean success;


    public ResultResponse() {
        this.success = true;
    }

    public ResultResponse(boolean success) {
        this.success = true;
    }

    public ResultResponse(List<?> list) {
        this(true);
        this.setRows(list);
    }


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public Long getTotal() {
        return this.total;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
        this.setTotal((long) rows.size());
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

