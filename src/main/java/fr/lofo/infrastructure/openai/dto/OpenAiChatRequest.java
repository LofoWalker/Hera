package fr.lofo.infrastructure.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAiChatRequest {

    private String model;
    private List<OpenAiMessage> messages;

    @Data
    @AllArgsConstructor
    public static class OpenAiMessage {
        private String role;
        private String content;
    }
}
