package fr.lofo.infrastructure.adapter;

import fr.lofo.domain.model.Message;
import fr.lofo.infrastructure.openai.OpenAiClient;
import fr.lofo.infrastructure.openai.dto.OpenAiChatRequest;
import fr.lofo.infrastructure.openai.dto.OpenAiChatResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenAiChatAdapterTest {

    /**
     * Tests if the chat method successfully returns the AI response message for valid input messages.
     */
    @Test
    void testChatReturnsAiResponse() {
        // Mock dependencies
        OpenAiClient mockedClient = mock(OpenAiClient.class);
        OpenAiChatAdapter adapter = new OpenAiChatAdapter();
        adapter.openAiClient = mockedClient;
        adapter.apiKey = "test-api-key";

        // Prepare test data
        List<Message> inputMessages = List.of(
                new Message("user", List.of("Hi AI")),
                new Message("assistant", List.of("Hello, user!"))
        );

        OpenAiChatResponse responseMock = new OpenAiChatResponse();
        OpenAiChatResponse.Choice choiceMock = new OpenAiChatResponse.Choice();
        OpenAiChatResponse.Message messageMock = new OpenAiChatResponse.Message();

        messageMock.setContent("How can I assist you today?");
        choiceMock.setMessage(messageMock);
        responseMock.setChoices(List.of(choiceMock));

        when(mockedClient.createChatCompletion(
                eq("Bearer test-api-key"),
                any(OpenAiChatRequest.class)
        )).thenReturn(responseMock);

        // Call the method to test
        String result = adapter.chat(inputMessages);

        // Verify result
        assertEquals("How can I assist you today?", result);
    }

    /**
     * Tests the scenario where the AI response contains no choices.
     */
    @Test
    void testChatWithNoChoicesReturnsEmptyString() {
        // Mock dependencies
        OpenAiClient mockedClient = mock(OpenAiClient.class);
        OpenAiChatAdapter adapter = new OpenAiChatAdapter();
        adapter.openAiClient = mockedClient;
        adapter.apiKey = "test-api-key";

        // Prepare test data
        List<Message> inputMessages = List.of(
                new Message("user", List.of("What is the weather?"))
        );

        OpenAiChatResponse responseMock = new OpenAiChatResponse();
        responseMock.setChoices(List.of()); // No choices in the response

        when(mockedClient.createChatCompletion(
                eq("Bearer test-api-key"),
                any(OpenAiChatRequest.class)
        )).thenReturn(responseMock);

        // Call the method to test
        String result = adapter.chat(inputMessages);

        // Verify result
        assertEquals("", result);
    }

    /**
     * Tests the scenario where the AI response is null.
     */
    @Test
    void testChatWithNullResponse() {
        // Mock dependencies
        OpenAiClient mockedClient = mock(OpenAiClient.class);
        OpenAiChatAdapter adapter = new OpenAiChatAdapter();
        adapter.openAiClient = mockedClient;
        adapter.apiKey = "test-api-key";

        // Prepare test data
        List<Message> inputMessages = List.of(
                new Message("user", List.of("Tell me a joke."))
        );

        when(mockedClient.createChatCompletion(
                eq("Bearer test-api-key"),
                any(OpenAiChatRequest.class)
        )).thenReturn(null); // Null response from the client

        // Call the method to test
        String result = adapter.chat(inputMessages);

        // Verify result
        assertEquals("", result);
    }

    /**
     * Tests the scenario where a frequent call uses messages with multi-line content.
     */
    @Test
    void testChatWithMultiLineContentInMessages() {
        // Mock dependencies
        OpenAiClient mockedClient = mock(OpenAiClient.class);
        OpenAiChatAdapter adapter = new OpenAiChatAdapter();
        adapter.openAiClient = mockedClient;
        adapter.apiKey = "test-api-key";

        // Prepare test data
        List<Message> inputMessages = List.of(
                new Message("user", List.of("Hello AI,", "Can you tell me a story?"))
        );

        OpenAiChatResponse responseMock = new OpenAiChatResponse();
        OpenAiChatResponse.Choice choiceMock = new OpenAiChatResponse.Choice();

        OpenAiChatResponse.Message messageMock = new OpenAiChatResponse.Message();

        messageMock.setContent("Sure! Once upon a time...");
        choiceMock.setMessage(messageMock);
        choiceMock.setMessage(messageMock);
        responseMock.setChoices(List.of(choiceMock));

        when(mockedClient.createChatCompletion(
                eq("Bearer test-api-key"),
                any(OpenAiChatRequest.class)
        )).thenReturn(responseMock);

        // Call the method to test
        String result = adapter.chat(inputMessages);

        // Verify result
        assertEquals("Sure! Once upon a time...", result);
    }
}