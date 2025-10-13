package org.example.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conversation {
    private final List<Message> messages;
    private final SystemInstruction systemInstruction;

    public Conversation(SystemInstruction systemInstruction) {
        if (systemInstruction == null) {
            throw new IllegalArgumentException("System instruction cannot be null");
        }
        this.systemInstruction = systemInstruction;
        this.messages = new ArrayList<>();
        this.messages.add(Message.system(List.of(systemInstruction.content())));
    }

    public void addUserMessage(List<String> content) {
        messages.add(Message.user(content));
    }

    public void addAssistantMessage(List<String> content) {
        messages.add(Message.assistant(content));
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public SystemInstruction getSystemInstruction() {
        return systemInstruction;
    }
}
