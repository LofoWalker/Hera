package org.example.infrastructure.openai;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.infrastructure.openai.dto.OpenAiChatRequest;
import org.example.infrastructure.openai.dto.OpenAiChatResponse;

/**
 * REST client for OpenAI API.
 */
@RegisterRestClient(configKey = "openai-api")
@Path("/v1")
public interface OpenAiClient {

    @POST
    @Path("/chat/completions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    OpenAiChatResponse createChatCompletion(
            @HeaderParam("Authorization") String authorization,
            OpenAiChatRequest request
    );
}
