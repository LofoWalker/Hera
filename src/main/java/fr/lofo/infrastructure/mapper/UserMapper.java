package fr.lofo.infrastructure.mapper;

import fr.lofo.domain.model.user.Email;
import fr.lofo.domain.model.user.Password;
import fr.lofo.domain.model.user.User;
import fr.lofo.domain.model.user.Username;
import fr.lofo.infrastructure.adapter.user.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity e) {
        if (e == null) return null;
        return User.create(e.getId(), new Username(e.getUsername()),
                new Email(e.getEmail()), new Password(e.getPasswordHash()),
                e.getAge(), e.getFirstName(), e.getLastName());
    }

    public static UserEntity toEntity(User d) {
        UserEntity e = new UserEntity();
        e.setId(d.getId());
        e.setUsername(d.getUsername().value());
        e.setEmail(d.getEmail().value());
        e.setPasswordHash(d.getPassword().hashed());
        e.setAge(d.getAge());
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        return e;
    }
}
