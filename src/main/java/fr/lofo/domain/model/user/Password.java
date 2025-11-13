package fr.lofo.domain.model.user;

import java.util.Objects;

/**
 * Domain value object representing a hashed password.
 * Construction expects hashed value. For hashing use a domain service / utility.
 */
public final class Password {
    private final String hashed;

    public Password(String hashed) {
        if (hashed == null || hashed.isBlank()) throw new IllegalArgumentException("hashed password required");
        this.hashed = hashed;
    }

    public String hashed() { return hashed; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password)) return false;
        return hashed.equals(((Password) o).hashed);
    }

    @Override
    public int hashCode() { return Objects.hash(hashed); }

    @Override
    public String toString() { return "***"; } // don't leak
}