package fr.lofo.application.service;

import fr.lofo.application.service.utils.PasswordHasher;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("Password hasher")
class PasswordHasherTest {

    @Inject PasswordHasher hasher;

    @Nested
    @DisplayName("hash")
    class Hash {
        @Test
        @DisplayName("should_produce_different_hashes_when_same_input")
        void should_produce_different_hashes_when_same_input() {
            String a = hasher.hash("abc123");
            String b = hasher.hash("abc123");
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("should_throw_when_input_is_null_or_blank")
        void should_throw_when_input_is_null_or_blank() {
            assertThrows(IllegalArgumentException.class, () -> hasher.hash(null));
            assertThrows(IllegalArgumentException.class, () -> hasher.hash("   "));
        }
    }

    @Nested
    @DisplayName("verify")
    class Verify {
        @Test
        @DisplayName("should_return_true_when_password_matches")
        void should_return_true_when_password_matches() {
            String h = hasher.hash("xyz");
            assertTrue(hasher.verify("xyz", h));
        }

        @Test
        @DisplayName("should_return_false_when_password_does_not_match")
        void should_return_false_when_password_does_not_match() {
            String h = hasher.hash("xyz");
            assertFalse(hasher.verify("nope", h));
        }

        @Test
        @DisplayName("should_return_false_when_hashed_is_null_or_blank")
        void should_return_false_when_hashed_is_null_or_blank() {
            assertFalse(hasher.verify("x", null));
            assertFalse(hasher.verify("x", "   "));
        }
    }
}
