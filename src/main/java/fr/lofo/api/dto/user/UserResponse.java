package fr.lofo.api.dto.user;

import fr.lofo.domain.model.user.User;

import java.util.UUID;

/**
 * DTO for user responses (without password).
 */
public class UserResponse {
    public UUID id;
    public String username;
    public String email;
    public Integer age;
    public String firstName;
    public String lastName;

    public static UserResponse from(User u) {
        UserResponse r = new UserResponse();
        r.id = u.getId();
        r.username = u.getUsername().value();
        r.email = u.getEmail().value();
        r.age = u.getAge();
        r.firstName = u.getFirstName();
        r.lastName = u.getLastName();
        return r;
    }
}
