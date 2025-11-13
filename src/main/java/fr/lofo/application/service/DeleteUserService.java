package fr.lofo.application.service;

import fr.lofo.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
public class DeleteUserService {

    private final UserRepositoryPort userRepository;

    public void handle(UUID id) {
        userRepository.removeById(id);
    }

}
