package fr.lofo.application.service;

import fr.lofo.application.command.CreateUserCommand;
import fr.lofo.application.exception.UserAlreadyExistsException;
import fr.lofo.application.service.utils.PasswordHasher;
import fr.lofo.domain.model.user.Email;
import fr.lofo.domain.model.user.Password;
import fr.lofo.domain.model.user.User;
import fr.lofo.domain.model.user.Username;
import fr.lofo.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
public class CreateUserService {

    private final UserRepositoryPort userRepository;
    private final PasswordHasher passwordHasher;

    public User handle(CreateUserCommand cmd) {
        userRepository.findByUsername(cmd.username()).ifPresent(u -> {
            throw new UserAlreadyExistsException("username already exists");
        });
        userRepository.findByEmail(cmd.email()).ifPresent(u -> {
            throw new UserAlreadyExistsException("email already exists");
        });

        Username username = new Username(cmd.username());
        Email email = new Email(cmd.email());
        String hashed = passwordHasher.hash(cmd.rawPassword());
        Password password = new Password(hashed);

        User user = User.create(UUID.randomUUID(), username, email, password, cmd.age(), cmd.firstName(), cmd.lastName());

        return userRepository.save(user);
    }
}