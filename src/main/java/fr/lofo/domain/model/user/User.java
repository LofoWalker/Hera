package fr.lofo.domain.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

/**
 * Domain model representing a User.
 */
@Setter
@Getter
public class User {
    private final UUID id;
    private Username username;
    private Email email;
    private Password password;
    private Integer age;
    private String firstName;
    private String lastName;

    private User(UUID id, Username username, Email email, Password password,
                 Integer age, String firstName, String lastName) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User create(UUID id, Username username, Email email, Password password,
                              Integer age, String firstName, String lastName) {
        return new User(id, username, email, password, age, firstName, lastName);
    }

    public void changeUsername(Username username) {
        this.username = Objects.requireNonNull(username);
    }

    public void changeEmail(Email email) {
        this.email = Objects.requireNonNull(email);
    }

    public void changePassword(Password password) {
        this.password = Objects.requireNonNull(password);
    }
}
