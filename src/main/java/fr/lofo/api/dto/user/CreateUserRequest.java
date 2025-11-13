package fr.lofo.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a user.
 */
public record CreateUserRequest(@NotBlank String username,
                                @NotBlank @Email String email,
                                @NotBlank String password,
                                Integer age,
                                String firstName,
                                String lastName) {
}
