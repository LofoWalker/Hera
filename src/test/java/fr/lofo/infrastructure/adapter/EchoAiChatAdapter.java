package fr.lofo.infrastructure.adapter;

import fr.lofo.domain.model.Message;
import fr.lofo.domain.port.AiChatPort;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import java.util.List;

@ApplicationScoped
@Alternative
@Priority(1)
public class EchoAiChatAdapter implements AiChatPort {
    @Override
    public String chat(List<Message> messages) {
        String lastUser = null;
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message m = messages.get(i);
            if ("user".equals(m.role()) && m.content() != null && !m.content().isEmpty()) {
                lastUser = m.content().get(0);
                break;
            }
        }
        return lastUser == null ? "" : "echo:" + lastUser;
    }
}
