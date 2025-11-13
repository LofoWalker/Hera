package fr.lofo.infrastructure.mapper;

import fr.lofo.domain.model.user.Email;
import fr.lofo.domain.model.user.Password;
import fr.lofo.domain.model.user.User;
import fr.lofo.domain.model.user.Username;
import fr.lofo.infrastructure.adapter.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserMapper")
class UserMapperTest {

    private User sampleDomain() {
        return User.create(UUID.randomUUID(), new Username("john"), new Email("john@example.com"), new Password("hashed"), 22, "J", "D");
    }

    @Nested
    @DisplayName("toEntity")
    class ToEntity {
        @Test
        @DisplayName("should_map_all_fields_when_domain_given")
        void should_map_all_fields_when_domain_given() {
            User d = sampleDomain();
            UserEntity e = UserMapper.toEntity(d);
            assertEquals(d.getId(), e.getId());
            assertEquals("john", e.getUsername());
            assertEquals("john@example.com", e.getEmail());
            assertEquals("hashed", e.getPasswordHash());
            assertEquals(22, e.getAge());
            assertEquals("J", e.getFirstName());
            assertEquals("D", e.getLastName());
        }
    }

    @Nested
    @DisplayName("toDomain")
    class ToDomain {
        @Test
        @DisplayName("should_map_all_fields_when_entity_given")
        void should_map_all_fields_when_entity_given() {
            UserEntity e = new UserEntity();
            UUID id = UUID.randomUUID();
            e.setId(id);
            e.setUsername("amy");
            e.setEmail("amy@example.com");
            e.setPasswordHash("ph");
            e.setAge(30);
            e.setFirstName("A");
            e.setLastName("B");

            User d = UserMapper.toDomain(e);
            assertEquals(id, d.getId());
            assertEquals("amy", d.getUsername().value());
            assertEquals("amy@example.com", d.getEmail().value());
            assertEquals("ph", d.getPassword().hashed());
            assertEquals(30, d.getAge());
            assertEquals("A", d.getFirstName());
            assertEquals("B", d.getLastName());
        }
    }
}
