package com.company.alpicoapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Barrier {

    private BarrierType type;
    private List<Integer> on;
    private Integer from; // Optional: might be null for non-time barriers
    private Integer until; // Optional: might be null for non-time barriers
    private String value;

    public BarrierType getType() {
        return type;
    }

    public Barrier setType(BarrierType type) {
        this.type = type;
        return this;
    }

    public List<Integer> getOn() {
        return on;
    }

    public Barrier setOn(List<Integer> on) {
        this.on = on;
        return this;
    }

    public Integer getFrom() {
        return from;
    }

    public Barrier setFrom(Integer from) {
        this.from = from;
        return this;
    }

    public Integer getUntil() {
        return until;
    }

    public Barrier setUntil(Integer until) {
        this.until = until;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Barrier setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Barrier{" +
                "type=" + type +
                ", on=" + on +
                ", from=" + from +
                ", until=" + until +
                ", value='" + value + '\'' +
                '}';
    }
}