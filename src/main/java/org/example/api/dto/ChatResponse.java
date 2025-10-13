package org.example.api.dto;

import java.util.List;

public class ChatResponse {
    private List<String> response;

    public ChatResponse() {
    }

    public ChatResponse(List<String> response) {
        this.response = response;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }
}
