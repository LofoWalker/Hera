package fr.lofo.domain.model.user;

import java.util.Objects;

public final class Username {
    private final String value;

    public Username(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("username is required");
        if (value.length() > 50) throw new IllegalArgumentException("username too long");
        this.value = value;
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username)) return false;
        return value.equals(((Username) o).value);
    }

    @Override
    public int hashCode() { return Objects.hash(value); }

    @Override
    public String toString() { return value; }
}