package fr.lofo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@DisplayName("User REST API")
class ExampleResourceTest {

    private String create(String username, String email) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\""+username+"\",\"email\":\""+email+"\",\"rawPassword\":\"pwd\",\"age\":25,\"firstName\":\"F\",\"lastName\":\"L\"}")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("should_create_user_when_request_is_valid")
        void should_create_user_when_request_is_valid() {
            given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"rest1\",\"email\":\"rest1@example.com\",\"rawPassword\":\"pwd\"}")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("username", equalTo("rest1"))
                .body("email", equalTo("rest1@example.com"))
                .body("id", notNullValue());
        }
    }

    @Nested
    @DisplayName("Query")
    class Query {
        @Test
        @DisplayName("should_get_user_when_exists")
        void should_get_user_when_exists() {
            String id = create("rest2","rest2@example.com");
            given()
                .when()
                .get("/api/users/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("username", equalTo("rest2"));
        }

        @Test
        @DisplayName("should_list_users_when_called")
        void should_list_users_when_called() {
            create("rest3","rest3@example.com");
            given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
        }

        @Test
        @DisplayName("should_return_404_when_user_not_found")
        void should_return_404_when_user_not_found() {
            String unknown = java.util.UUID.randomUUID().toString();
            given()
                .when()
                .get("/api/users/" + unknown)
                .then()
                .statusCode(404);
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("should_update_user_when_request_is_valid")
        void should_update_user_when_request_is_valid() {
            String id = create("rest4","rest4@example.com");
            given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"rest4b\",\"email\":\"rest4b@example.com\",\"age\":26}")
                .when()
                .put("/api/users/" + id)
                .then()
                .statusCode(200)
                .body("username", equalTo("rest4b"))
                .body("email", equalTo("rest4b@example.com"))
                .body("age", equalTo(26));
        }

        @Test
        @DisplayName("should_return_400_when_email_invalid")
        void should_return_400_when_email_invalid() {
            String id = create("rest4c","rest4c@example.com");
            given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"not-an-email\"}")
                .when()
                .put("/api/users/" + id)
                .then()
                .statusCode(400);
        }
    }

    @Nested
    @DisplayName("Delete")
    class Delete {
        @Test
        @DisplayName("should_delete_user_when_exists")
        void should_delete_user_when_exists() {
            String id = create("rest5","rest5@example.com");
            given()
                .when()
                .delete("/api/users/" + id)
                .then()
                .statusCode(204);
            given()
                .when()
                .get("/api/users/" + id)
                .then()
                .statusCode(404);
        }
    }
}