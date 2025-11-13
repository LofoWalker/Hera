package fr.lofo.application;

import fr.lofo.api.dto.ChatRequest;
import fr.lofo.api.dto.ChatResponse;
import fr.lofo.domain.model.Conversation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("Chat feature")
class ChatServiceTest {

    @Inject ChatService chatService;

    @Nested
    @DisplayName("service")
    class ServiceCases {
        @Test
        @DisplayName("should_create_conversation_when_called")
        void should_create_conversation_when_called() {
            Conversation c = chatService.createConversation();
            assertNotNull(c);
            assertFalse(c.getMessages().isEmpty());
            assertEquals("system", c.getMessages().getFirst().role());
        }

        @Test
        @DisplayName("should_send_and_receive_when_message_is_sent")
        void should_send_and_receive_when_message_is_sent() {
            Conversation c = chatService.createConversation();
            var response = chatService.sendMessage(c, java.util.List.of("hello"));
            assertEquals(1, response.size());
            assertTrue(response.get(0).startsWith("echo:"));
            assertEquals("assistant", c.getMessages().getLast().role());
        }
    }

    @Nested
    @DisplayName("resource")
    class ResourceCases {
        @Test
        @DisplayName("should_return_ai_response_when_chat_endpoint_called")
        void should_return_ai_response_when_chat_endpoint_called() {
            given()
                .contentType("application/json")
                .body(new ChatRequest(java.util.List.of("hello")))
            .when()
                .post("/api/chat")
            .then()
                .statusCode(200)
                .body("response[0]", equalTo("echo:hello"));
        }
    }
}
