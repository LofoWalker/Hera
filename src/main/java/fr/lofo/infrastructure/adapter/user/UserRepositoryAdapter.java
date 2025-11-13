package fr.lofo.infrastructure.adapter.user;

import fr.lofo.domain.model.user.User;
import fr.lofo.domain.port.UserRepositoryPort;
import fr.lofo.infrastructure.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort, PanacheRepositoryBase<UserEntity, UUID> {

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        persist(entity);
        return UserMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public User update(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity merged = getEntityManager().merge(entity);
        return UserMapper.toDomain(merged);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return find("username", username)
                .firstResultOptional()
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return find("email", email)
                .firstResultOptional()
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> getById(UUID id) {
        return findByIdOptional(id).map(UserMapper::toDomain);
    }

    @Override
    public List<User> listUsers() {
        return listAll().stream().map(UserMapper::toDomain).toList();
    }

    @Override
    @Transactional
    public void removeById(UUID id) {
        findByIdOptional(id).ifPresent(this::delete);
    }
}