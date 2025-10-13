package org.example.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.api.dto.ChatRequest;
import org.example.api.dto.ChatResponse;
import org.example.application.ChatService;
import org.example.domain.model.Conversation;

/**
 * REST API endpoint for AI chat functionality.
 */
@Path("/api/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatResource {

    @Inject
    ChatService chatService;

    /**
     * Send a message to the AI and get a response.
     * Creates a new conversation with predefined system instruction from domain for each request.
     * Users cannot provide their own system instructions.
     *
     * @param chatRequest the request containing user message
     * @return the AI's response
     */
    @POST
    public ChatResponse chat(ChatRequest chatRequest) {
        Conversation conversation = chatService.createConversation();
        var aiResponse = chatService.sendMessage(conversation, chatRequest.getMessage());

        return new ChatResponse(aiResponse);
    }
}
