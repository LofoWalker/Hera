package org.example.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.domain.model.Conversation;
import org.example.domain.port.AiChatPort;

import java.util.List;

/**
 * Application service for managing chat conversations.
 * This orchestrates the domain logic and coordinates with infrastructure through ports.
 */
@ApplicationScoped
public class ChatService {

    private final AiChatPort aiChatPort;

    @Inject
    public ChatService(AiChatPort aiChatPort) {
        this.aiChatPort = aiChatPort;
    }

    /**
     * Sends a user message and gets AI response.
     *
     * @param conversation the current conversation
     * @param userMessage the user's message
     * @return the AI's response
     */
    public List<String> sendMessage(Conversation conversation, List<String> userMessage) {
        conversation.addUserMessage(userMessage);
        String aiResponse = aiChatPort.chat(conversation.getMessages());
        List<String> aiResponseList = List.of(aiResponse);
        conversation.addAssistantMessage(aiResponseList);
        return aiResponseList;
    }

    /**
     * Creates a new conversation with predefined system instructions from the domain.
     *
     * @return a new conversation with default system instruction
     */
    public Conversation createConversation() {
        return new Conversation(org.example.domain.model.SystemInstruction.createDefault());
    }
}
