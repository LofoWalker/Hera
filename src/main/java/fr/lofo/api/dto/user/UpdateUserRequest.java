package fr.lofo.api.dto.user;

import jakarta.validation.constraints.Email;

/**
 * DTO for updating a user. All fields are optional.
 */
public class UpdateUserRequest {
    public String username;
    @Email
    public String email;
    public String password;
    public Integer age;
    public String firstName;
    public String lastName;
}
