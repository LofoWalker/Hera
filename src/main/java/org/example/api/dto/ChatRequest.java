package org.example.api.dto;

import java.util.List;

public class ChatRequest {
    private List<String> message;

    public ChatRequest() {
    }

    public ChatRequest(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
