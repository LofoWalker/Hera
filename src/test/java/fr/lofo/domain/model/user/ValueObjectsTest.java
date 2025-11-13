package fr.lofo.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User value objects")
class ValueObjectsTest {

    @Nested
    @DisplayName("Email")
    class EmailTests {
        @Test
        @DisplayName("should_create_when_email_is_valid")
        void should_create_when_email_is_valid() {
            Email e = new Email("Test@Example.com");
            assertEquals("test@example.com", e.value());
        }

        @Test
        @DisplayName("should_throw_when_email_is_invalid")
        void should_throw_when_email_is_invalid() {
            assertThrows(IllegalArgumentException.class, () -> new Email("bad"));
        }
    }

    @Nested
    @DisplayName("Username")
    class UsernameTests {
        @Test
        @DisplayName("should_create_when_username_is_valid")
        void should_create_when_username_is_valid() {
            Username u = new Username("john");
            assertEquals("john", u.value());
        }

        @Test
        @DisplayName("should_throw_when_username_is_blank")
        void should_throw_when_username_is_blank() {
            assertThrows(IllegalArgumentException.class, () -> new Username(" "));
        }

        @Test
        @DisplayName("should_throw_when_username_is_too_long")
        void should_throw_when_username_is_too_long() {
            String longName = "x".repeat(51);
            assertThrows(IllegalArgumentException.class, () -> new Username(longName));
        }
    }
}
