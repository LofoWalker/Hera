package org.example.infrastructure.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenAiChatRequest {

    private String model;
    private List<OpenAiMessage> messages;

    public OpenAiChatRequest() {
    }

    public OpenAiChatRequest(String model, List<OpenAiMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<OpenAiMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<OpenAiMessage> messages) {
        this.messages = messages;
    }

    public static class OpenAiMessage {
        private String role;
        private String content;

        public OpenAiMessage() {
        }

        public OpenAiMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
