package com.company.alpicoapi.dto;

public class Issue {
    private String code;
    private String expected;
    private String received;
    private String[] path;
    private String message;
    private Params params;

    public String getCode() {
        return code;
    }

    public Issue setCode(String code) {
        this.code = code;
        return this;
    }

    public String getExpected() {
        return expected;
    }

    public Issue setExpected(String expected) {
        this.expected = expected;
        return this;
    }

    public String getReceived() {
        return received;
    }

    public Issue setReceived(String received) {
        this.received = received;
        return this;
    }

    public String[] getPath() {
        return path;
    }

    public Issue setPath(String[] path) {
        this.path = path;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Issue setMessage(String message) {
        this.message = message;
        return this;
    }

    public Params getParams() {
        return params;
    }

    public Issue setParams(Params params) {
        this.params = params;
        return this;
    }
}
