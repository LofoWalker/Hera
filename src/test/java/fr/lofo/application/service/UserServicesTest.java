package fr.lofo.application.service;

import fr.lofo.application.command.CreateUserCommand;
import fr.lofo.application.command.UpdateUserCommand;
import fr.lofo.application.exception.UserAlreadyExistsException;
import fr.lofo.application.exception.UserNotFoundException;
import fr.lofo.application.service.utils.PasswordHasher;
import fr.lofo.api.dto.user.UserResponse;
import fr.lofo.domain.model.user.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("User services")
class UserServicesTest {

    @Inject CreateUserService createUserService;
    @Inject UpdateUserService updateUserService;
    @Inject DeleteUserService deleteUserService;
    @Inject QueryUserService queryUserService;
    @Inject PasswordHasher passwordHasher;

    private CreateUserCommand sample(String username, String email) {
        return new CreateUserCommand(username, email, "secret123", 30, "John", "Doe");
        
    }

    @Nested
    @DisplayName("CreateUserService")
    class CreateTests {
        @Test
        @DisplayName("should_create_user_when_input_is_valid")
        void should_create_user_when_input_is_valid() {
            CreateUserCommand cmd = sample("alice","alice@example.com");
            User u = createUserService.handle(cmd);
            assertNotNull(u.getId());
            assertEquals("alice", u.getUsername().value());
            assertTrue(passwordHasher.verify("secret123", u.getPassword().hashed()));
        }

        @Test
        @DisplayName("should_fail_when_username_already_exists")
        void should_fail_when_username_already_exists() {
            createUserService.handle(sample("bob","bob@example.com"));
            assertThrows(UserAlreadyExistsException.class, () -> createUserService.handle(sample("bob","bob2@example.com")));
        }

        @Test
        @DisplayName("should_fail_when_email_already_exists")
        void should_fail_when_email_already_exists() {
            createUserService.handle(sample("carol","carol@example.com"));
            assertThrows(UserAlreadyExistsException.class, () -> createUserService.handle(sample("carol2","carol@example.com")));
        }
    }

    @Nested
    @DisplayName("QueryUserService")
    class QueryTests {
        @Test
        @DisplayName("should_find_all_when_users_exist")
        void should_find_all_when_users_exist() {
            createUserService.handle(sample("dave","dave@example.com"));
            createUserService.handle(sample("erin","erin@example.com"));
            List<UserResponse> all = queryUserService.findAll();
            assertTrue(all.size() >= 2);
        }

        @Test
        @DisplayName("should_find_by_id_when_user_exists")
        void should_find_by_id_when_user_exists() {
            User created = createUserService.handle(sample("frank","frank@example.com"));
            Optional<UserResponse> found = queryUserService.findById(created.getId());
            assertTrue(found.isPresent());
            assertEquals("frank", found.get().username);
        }
    }

    @Nested
    @DisplayName("UpdateUserService")
    class UpdateTests {
        @Test
        @DisplayName("should_update_fields_when_input_is_valid")
        void should_update_fields_when_input_is_valid() {
            User created = createUserService.handle(sample("gina","gina@example.com"));
            UpdateUserCommand cmd = new UpdateUserCommand(created.getId(), "gina2", "gina2@example.com", "newpass", 31, "G", "Ina");
            User updated = updateUserService.handle(cmd);
            assertEquals("gina2", updated.getUsername().value());
            assertEquals("gina2@example.com", updated.getEmail().value());
            assertEquals(31, updated.getAge());
            assertTrue(passwordHasher.verify("newpass", updated.getPassword().hashed()));
        }

        @Test
        @DisplayName("should_fail_when_user_not_found")
        void should_fail_when_user_not_found() {
            UUID unknown = UUID.randomUUID();
            UpdateUserCommand cmd = new UpdateUserCommand(unknown, null, null, null, null, null, null);
            assertThrows(UserNotFoundException.class, () -> updateUserService.handle(cmd));
        }

        @Test
        @DisplayName("should_fail_when_new_username_conflicts")
        void should_fail_when_new_username_conflicts() {
            User a = createUserService.handle(sample("henry","henry@example.com"));
            createUserService.handle(sample("ivy","ivy@example.com"));
            UpdateUserCommand cmd = new UpdateUserCommand(a.getId(), "ivy", null, null, null, null, null);
            assertThrows(UserAlreadyExistsException.class, () -> updateUserService.handle(cmd));
        }

        @Test
        @DisplayName("should_fail_when_new_email_conflicts")
        void should_fail_when_new_email_conflicts() {
            User a = createUserService.handle(sample("jack","jack@example.com"));
            createUserService.handle(sample("kate","kate@example.com"));
            UpdateUserCommand cmd = new UpdateUserCommand(a.getId(), null, "kate@example.com", null, null, null, null);
            assertThrows(UserAlreadyExistsException.class, () -> updateUserService.handle(cmd));
        }
    }

    @Nested
    @DisplayName("DeleteUserService")
    class DeleteTests {
        @Test
        @DisplayName("should_delete_user_when_id_exists")
        void should_delete_user_when_id_exists() {
            User created = createUserService.handle(sample("leo","leo@example.com"));
            deleteUserService.handle(created.getId());
            Optional<UserResponse> found = queryUserService.findById(created.getId());
            assertTrue(found.isEmpty());
        }
    }
}
