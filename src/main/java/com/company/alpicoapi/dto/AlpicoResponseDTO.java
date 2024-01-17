package com.company.alpicoapi.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlpicoResponseDTO {
    private boolean success;
    private String payload;
    private Issue[] issues;
    private String name;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public AlpicoResponseDTO setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public AlpicoResponseDTO setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public Issue[] getIssues() {
        return issues;
    }

    public AlpicoResponseDTO setIssues(Issue[] issues) {
        this.issues = issues;
        return this;
    }

    public String getName() {
        return name;
    }

    public AlpicoResponseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AlpicoResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}