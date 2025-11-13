package fr.lofo.application.command;

public record CreateUserCommand(
        String username,
        String email,
        String rawPassword,
        Integer age,
        String firstName,
        String lastName
) {}