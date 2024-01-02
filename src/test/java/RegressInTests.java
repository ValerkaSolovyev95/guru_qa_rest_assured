import io.restassured.RestAssured;
import models.CreateWorkerRequest;
import models.CreateWorkerResponse;
import models.SuccessLoginRequest;
import models.SuccessLoginResponse;
import models.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.CreateWorkerSpec.createWorkerRequestSpecification;
import static specs.CreateWorkerSpec.createWorkerResponseSpecification;
import static specs.LoginSpec.loginRequestSpecification;
import static specs.LoginSpec.loginResponseSpecification;
import static specs.UserIdSpec.userIdRequestSpecification;
import static specs.UserIdSpec.userIdResponseSpecification;

public class RegressInTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void checkUserIdTest() {
        UserData response = step("Check user id", () -> given(userIdRequestSpecification)
                .when()
                .get("/api/users/{id}", 2)
                .then()
                .spec(userIdResponseSpecification)
                .assertThat()
                .statusCode(200)
                .extract().as(UserData.class)
        );
        step("Check user id", () -> Assertions.assertEquals(2, response.data.id));
    }

    @Test
    void whenSingleUserIdNotInListIdTest() {
        step("Check status code 404", () -> given(userIdRequestSpecification)
                .when()
                .get("/api/unknown/{id}", 23)
                .then()
                .spec(userIdResponseSpecification)
                .assertThat()
                .statusCode(404)
        );
    }

    @Test
    void checkWhatNameFromBodyEqualsNameFromResponseTest() {
        CreateWorkerRequest requestBody = new CreateWorkerRequest();
        step("Filling request body object", () -> {
            requestBody.setName("morpheus");
            requestBody.setJob("leader");
        });
        CreateWorkerResponse response = step("Send request", () -> given(createWorkerRequestSpecification)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(createWorkerResponseSpecification)
                .extract().as(CreateWorkerResponse.class)
        );
        step("Check name", () -> Assertions.assertEquals("morpheus", response.getName()));
    }

    @Test
    void successLoginTest() {
        SuccessLoginRequest requestBody = new SuccessLoginRequest();
        step("Filling request body object", () -> {
            requestBody.setEmail("eve.holt@reqres.in");
            requestBody.setPassword("cityslicka");
        });
        SuccessLoginResponse response = step("Send request", () -> given(loginRequestSpecification)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(loginResponseSpecification)
                .extract().as(SuccessLoginResponse.class)
        );
        step("Check token", () -> Assertions.assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successDeleteTest() {
        step("Delete user with id 2", () -> given(userIdRequestSpecification)
                .when()
                .delete("api/users/{id}", 2)
                .then()
                .spec(userIdResponseSpecification)
                .statusCode(204));
    }
}
