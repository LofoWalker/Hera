package fr.lofo.application.command;

import java.util.UUID;

public record UpdateUserCommand(
        UUID id,
        String username,
        String email,
        String rawPassword,
        Integer age,
        String firstName,
        String lastName
) {}