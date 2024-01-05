import io.restassured.RestAssured;
import models.CreateWorker;
import models.SuccessLoginRequest;
import models.SuccessLoginResponse;
import models.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegressInSpec.requestRegressInSpecification;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode200;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode201;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode204;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode404;

public class RegressInTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void checkUserIdTest() {
        UserData response = step("Check user id", () -> given(requestRegressInSpecification)
                .when()
                .get("/users/{id}", 2)
                .then()
                .spec(responseRegressInSpecificationStatusCode200)
                .extract().as(UserData.class)
        );
        step("Check user id", () -> assertEquals(2, response.getData().getId()));
    }

    @Test
    void whenSingleUserIdNotInListIdTest() {
        step("Check status code 404", () -> given(requestRegressInSpecification)
                .when()
                .get("/unknown/{id}", 23)
                .then()
                .spec(responseRegressInSpecificationStatusCode404)
        );
    }

    @Test
    void checkWhatNameFromBodyEqualsNameFromResponseTest() {
        CreateWorker requestBody = new CreateWorker();
        step("Filling request body object", () -> {
            requestBody.setName("morpheus");
            requestBody.setJob("leader");
        });
        CreateWorker response = step("Send request", () -> given(requestRegressInSpecification)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .spec(responseRegressInSpecificationStatusCode201)
                .extract().as(CreateWorker.class)
        );
        step("Check name", () -> assertEquals("morpheus", response.getName()));
    }

    @Test
    void successLoginTest() {
        SuccessLoginRequest requestBody = new SuccessLoginRequest();
        step("Filling request body object", () -> {
            requestBody.setEmail("eve.holt@reqres.in");
            requestBody.setPassword("cityslicka");
        });
        SuccessLoginResponse response = step("Send request", () -> given(requestRegressInSpecification)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .spec(responseRegressInSpecificationStatusCode200)
                .extract().as(SuccessLoginResponse.class)
        );
        step("Check token", () -> assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successDeleteTest() {
        step("Delete user with id 2", () -> given(requestRegressInSpecification)
                .when()
                .delete("/users/{id}", 2)
                .then()
                .spec(responseRegressInSpecificationStatusCode204)
        );
    }
}
