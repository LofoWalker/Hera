package fr.lofo.application.service;


import fr.lofo.application.command.UpdateUserCommand;
import fr.lofo.application.exception.UserAlreadyExistsException;
import fr.lofo.application.exception.UserNotFoundException;
import fr.lofo.application.service.utils.PasswordHasher;
import fr.lofo.domain.model.user.Email;
import fr.lofo.domain.model.user.Password;
import fr.lofo.domain.model.user.User;
import fr.lofo.domain.model.user.Username;
import fr.lofo.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class UpdateUserService {

    private final UserRepositoryPort userRepository;
    private final PasswordHasher passwordHasher;

    public User handle(UpdateUserCommand cmd) {
        User existing = userRepository.getById(cmd.id())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + cmd.id()));

        updateUsername(cmd, existing);
        updateEmail(cmd, existing);
        updatePassword(cmd, existing);
        updateAge(cmd, existing);
        updateFirstname(cmd, existing);
        updateLastname(cmd, existing);

        return userRepository.update(existing);
    }

    private static void updateLastname(UpdateUserCommand cmd, User existing) {
        if (cmd.lastName() != null)
            existing.setLastName(cmd.lastName());
    }

    private static void updateFirstname(UpdateUserCommand cmd, User existing) {
        if (cmd.firstName() != null)
            existing.setFirstName(cmd.firstName());
    }

    private static void updateAge(UpdateUserCommand cmd, User existing) {
        if (cmd.age() != null)
            existing.setAge(cmd.age());
    }

    private void updatePassword(UpdateUserCommand cmd, User existing) {
        if (cmd.rawPassword() != null && !cmd.rawPassword().isBlank()) {
            String hashed = passwordHasher.hash(cmd.rawPassword());
            existing.changePassword(new Password(hashed));
        }
    }

    private void updateEmail(UpdateUserCommand cmd, User existing) {
        if (cmd.email() != null && !cmd.email().equals(existing.getEmail().value())) {
            Email newEmail = new Email(cmd.email());
            userRepository.findByEmail(newEmail.value()).ifPresent(u -> {
                throw new UserAlreadyExistsException("email already exists");
            });
            existing.changeEmail(newEmail);
        }
    }

    private void updateUsername(UpdateUserCommand cmd, User existing) {
        if (cmd.username() != null && !cmd.username().equals(existing.getUsername().value())) {
            Username newUsername = new Username(cmd.username());
            userRepository.findByUsername(newUsername.value()).ifPresent(u -> {
                throw new UserAlreadyExistsException("username already exists");
            });
            existing.changeUsername(newUsername);
        }
    }
}