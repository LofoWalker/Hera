package fr.lofo.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User domain")
class UserTest {

    private User newUser() {
        return User.create(UUID.randomUUID(), new Username("u1"), new Email("u1@example.com"), new Password("hash"), 20, "F", "L");
    }

    @Nested
    @DisplayName("mutators")
    class Mutators {
        @Test
        @DisplayName("should_change_username_when_valid")
        void should_change_username_when_valid() {
            User u = newUser();
            u.changeUsername(new Username("u2"));
            assertEquals("u2", u.getUsername().value());
        }

        @Test
        @DisplayName("should_change_email_when_valid")
        void should_change_email_when_valid() {
            User u = newUser();
            u.changeEmail(new Email("u2@example.com"));
            assertEquals("u2@example.com", u.getEmail().value());
        }

        @Test
        @DisplayName("should_change_password_when_valid")
        void should_change_password_when_valid() {
            User u = newUser();
            u.changePassword(new Password("hash2"));
            assertEquals("hash2", u.getPassword().hashed());
        }

        @Test
        @DisplayName("should_set_primitives_when_called")
        void should_set_primitives_when_called() {
            User u = newUser();
            u.setAge(33);
            u.setFirstName("A");
            u.setLastName("B");
            assertEquals(33, u.getAge());
            assertEquals("A", u.getFirstName());
            assertEquals("B", u.getLastName());
        }
    }
}
