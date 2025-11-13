package fr.lofo.domain.port;

import fr.lofo.domain.model.Message;

import java.util.List;

/**
 * Port interface for AI chat functionality.
 * This is the domain boundary that infrastructure adapters must implement.
 */
public interface AiChatPort {
    /**
     * Sends a list of messages to the AI and gets a response.
     *
     * @param messages the conversation history including system instructions
     * @return the AI's response message
     */
    String chat(List<Message> messages);
}
