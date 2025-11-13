package fr.lofo.infrastructure.openai;

import fr.lofo.infrastructure.openai.dto.OpenAiChatRequest;
import fr.lofo.infrastructure.openai.dto.OpenAiChatResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

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
