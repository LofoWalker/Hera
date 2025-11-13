package fr.lofo.domain.port;

import fr.lofo.domain.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Hexagonal port for user persistence operations.
 */
public interface UserRepositoryPort {
    User save(User user);
    User update(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> getById(UUID id);
    List<User> listUsers();
    void removeById(UUID id);
}