package fr.lofo.api;

import fr.lofo.api.dto.user.UserResponse;
import fr.lofo.application.command.CreateUserCommand;
import fr.lofo.application.command.UpdateUserCommand;
import fr.lofo.application.service.CreateUserService;
import fr.lofo.application.service.DeleteUserService;
import fr.lofo.application.service.QueryUserService;
import fr.lofo.application.service.UpdateUserService;
import fr.lofo.domain.model.user.User;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    CreateUserService createUserService;
    QueryUserService queryUserService;
    UpdateUserService updateUserService;
    DeleteUserService deleteUserService;


    @POST
    public Response create(@Valid CreateUserCommand command) {
        User created = createUserService.handle(command);
        return Response
                .created(URI.create("/api/users/" + created.getId()))
                .entity(UserResponse.from(created))
                .build();
    }

    @GET
    public List<UserResponse> getAll() {
        return queryUserService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") UUID id) {
        Optional<UserResponse> user = queryUserService.findById(id);

        return user
                .map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, @Valid UpdateUserCommand command) {
        try {
            UpdateUserCommand withId = new UpdateUserCommand(
                    id,
                    command.username(),
                    command.email(),
                    command.rawPassword(),
                    command.age(),
                    command.firstName(),
                    command.lastName()
            );
            User updated = updateUserService.handle(withId);
            return Response.ok(UserResponse.from(updated)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        deleteUserService.handle(id);
        return Response.noContent().build();
    }
}
