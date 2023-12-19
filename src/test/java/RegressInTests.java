import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegressInTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void checkUserIdTest() {
        given().
                when().
                get("/api/users/{id}", 2).
                then().
                log().all().
                assertThat().
                statusCode(200).
                log().all().
                body("data.id", equalTo(2));
    }

    @Test
    void whenSingleUserIdNotInListIdTest() {
        given().
                when().
                log().all().
                get("/api/users/{id}", 23).
                then().
                log().all().
                assertThat().
                statusCode(404);
    }

    @Test
    void checkWhatNameFromBodyEqualsNameFromResponseTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        given().
                body(requestBody).
                contentType(ContentType.JSON).
                when().
                log().all().
                post("/api/users").
                then().
                log().all().
                statusCode(201).
                body("name", is("morpheus"));
    }

    @Test
    void successLoginTest() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        given().
                body(requestBody).
                contentType(ContentType.JSON).
                when().
                log().all().
                post("/api/login").
                then().
                log().all().
                statusCode(200).
                body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successDeleteTest() {
        given().
                when().
                delete("api/users/{id}",2).
                then().
                statusCode(204);
    }
}
