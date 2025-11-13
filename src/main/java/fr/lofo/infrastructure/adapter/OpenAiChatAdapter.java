package fr.lofo.infrastructure.adapter;

import fr.lofo.domain.model.Message;
import fr.lofo.domain.port.AiChatPort;
import fr.lofo.infrastructure.openai.OpenAiClient;
import fr.lofo.infrastructure.openai.dto.OpenAiChatRequest;
import fr.lofo.infrastructure.openai.dto.OpenAiChatResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().getFirst().getMessage().getContent();
        }

        return "";
    }
}
