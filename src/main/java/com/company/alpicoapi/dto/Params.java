package com.company.alpicoapi.dto;

public class Params {
    private Expected expected;
    private Integer actual;

    public Expected getExpected() {
        return expected;
    }

    public Params setExpected(Expected expected) {
        this.expected = expected;
        return this;
    }

    public Integer getActual() {
        return actual;
    }

    public Params setActual(Integer actual) {
        this.actual = actual;
        return this;
    }
}

