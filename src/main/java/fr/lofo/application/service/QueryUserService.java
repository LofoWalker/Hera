package fr.lofo.application.service;

import fr.lofo.api.dto.user.UserResponse;
import fr.lofo.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
public class QueryUserService {
    private final UserRepositoryPort repository;

    public Optional<UserResponse> findById(UUID id) {
        return repository.getById(id)
                .map(UserResponse::from);
    }

    public List<UserResponse> findAll() {
        return repository.listUsers()
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}