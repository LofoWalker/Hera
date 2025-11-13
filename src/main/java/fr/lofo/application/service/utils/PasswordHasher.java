package fr.lofo.application.service.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Simple wrapper around BCrypt. Using bcrypt is preferred to plain SHA-256 for password hashing.
 */
@ApplicationScoped
public class PasswordHasher {

    public String hash(String raw) {
        if (raw == null || raw.isBlank()) throw new IllegalArgumentException("password required");
        int logRounds = 10;
        return BCrypt.hashpw(raw, BCrypt.gensalt(logRounds));
    }

    public boolean verify(String raw, String hashed) {
        if (hashed == null || hashed.isBlank()) return false;
        return BCrypt.checkpw(raw, hashed);
    }
}