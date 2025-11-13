package fr.lofo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conversation")
class ConversationTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorCases {
        @Test
        @DisplayName("should_throw_when_system_instruction_is_null")
        void should_throw_when_system_instruction_is_null() {
            assertThrows(IllegalArgumentException.class, () -> new Conversation(null));
        }

        @Test
        @DisplayName("should_add_system_message_when_created")
        void should_add_system_message_when_created() {
            Conversation c = new Conversation(new SystemInstruction("be nice"));
            assertEquals(1, c.getMessages().size());
            assertEquals("system", c.getMessages().get(0).role());
        }
    }

    @Nested
    @DisplayName("messages")
    class MessagesCases {
        @Test
        @DisplayName("should_append_user_and_assistant_when_added")
        void should_append_user_and_assistant_when_added() {
            Conversation c = new Conversation(new SystemInstruction("hello"));
            c.addUserMessage(List.of("hi"));
            c.addAssistantMessage(List.of("yo"));
            assertEquals(3, c.getMessages().size());
            assertEquals("user", c.getMessages().get(1).role());
            assertEquals("assistant", c.getMessages().get(2).role());
        }

        @Test
        @DisplayName("should_return_immutable_messages_when_accessed")
        void should_return_immutable_messages_when_accessed() {
            Conversation c = new Conversation(new SystemInstruction("hello"));
            assertThrows(UnsupportedOperationException.class, () -> c.getMessages().add(Message.user(List.of("x"))));
        }
    }
}
