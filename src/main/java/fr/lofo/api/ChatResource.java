package fr.lofo.api;

import fr.lofo.api.dto.ChatRequest;
import fr.lofo.api.dto.ChatResponse;
import fr.lofo.application.ChatService;
import fr.lofo.domain.model.Conversation;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
