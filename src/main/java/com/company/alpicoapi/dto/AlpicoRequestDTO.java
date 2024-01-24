package com.company.alpicoapi.dto;

import java.util.HashMap;
import java.util.Map;

public class AlpicoRequestDTO {

    private String payload;
    private String proof;
    private Map<Integer, String> responses = new HashMap<>();

    public String getPayload() {
        return payload;
    }

    public AlpicoRequestDTO setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public String getProof() {
        return proof;
    }

    public AlpicoRequestDTO setProof(String proof) {
        this.proof = proof;
        return this;
    }

    public Map<Integer, String> getResponses() {
        return responses;
    }

    public AlpicoRequestDTO setResponses(Map<Integer, String> responses) {
        this.responses = responses;
        return this;
    }
}
