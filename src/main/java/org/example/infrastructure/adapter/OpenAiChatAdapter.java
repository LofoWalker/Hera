package org.example.infrastructure.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.domain.model.Message;
import org.example.domain.port.AiChatPort;
import org.example.infrastructure.openai.OpenAiClient;
import org.example.infrastructure.openai.dto.OpenAiChatRequest;
import org.example.infrastructure.openai.dto.OpenAiChatResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Infrastructure adapter that implements the AiChatPort using OpenAI API.
 */
@ApplicationScoped
public class OpenAiChatAdapter implements AiChatPort {

    @Inject
    @RestClient
    OpenAiClient openAiClient;

    @ConfigProperty(name = "openai.api.key")
    String apiKey;

    @Override
    public String chat(List<Message> messages) {
        List<OpenAiChatRequest.OpenAiMessage> openAiMessages = messages.stream()
                .map(msg -> new OpenAiChatRequest.OpenAiMessage(msg.role(), String.join("\n", msg.content())))
                .collect(Collectors.toList());

        OpenAiChatRequest request = new OpenAiChatRequest("gpt-4.1-nano", openAiMessages);

        OpenAiChatResponse response = openAiClient.createChatCompletion(
                "Bearer " + apiKey,
                request
        );

        if (response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "";
    }
}
