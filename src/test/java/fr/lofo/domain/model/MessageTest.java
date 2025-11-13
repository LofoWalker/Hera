package fr.lofo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Message")
class MessageTest {

    @Nested
    @DisplayName("factory")
    class FactoryCases {
        @Test
        @DisplayName("should_create_system_when_called")
        void should_create_system_when_called() {
            Message m = Message.system(List.of("a"));
            assertEquals("system", m.role());
            assertEquals(List.of("a"), m.content());
        }

        @Test
        @DisplayName("should_create_user_when_called")
        void should_create_user_when_called() {
            Message m = Message.user(List.of("b"));
            assertEquals("user", m.role());
            assertEquals(List.of("b"), m.content());
        }

        @Test
        @DisplayName("should_create_assistant_when_called")
        void should_create_assistant_when_called() {
            Message m = Message.assistant(List.of("c"));
            assertEquals("assistant", m.role());
            assertEquals(List.of("c"), m.content());
        }
    }
}
