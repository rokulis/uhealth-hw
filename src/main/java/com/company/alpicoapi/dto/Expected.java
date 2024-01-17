package com.company.alpicoapi.dto;

public class Expected {
    private Integer after;
    private Integer before;

    public Integer getAfter() {
        return after;
    }

    public Expected setAfter(Integer after) {
        this.after = after;
        return this;
    }

    public Integer getBefore() {
        return before;
    }

    public Expected setBefore(Integer before) {
        this.before = before;
        return this;
    }
}