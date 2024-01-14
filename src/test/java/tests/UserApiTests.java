package tests;

import models.CreateWorker;
import models.UserData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RegressInSpec.requestRegressInSpecification;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode200;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode201;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode204;
import static specs.RegressInSpec.responseRegressInSpecificationStatusCode404;

@Tag("user_api_test")
public class UserApiTests extends BaseTest{

    @Test
    void checkUserIdTest() {
        UserData response = step("Check user id", () -> given(requestRegressInSpecification)
                .when()
                .get("/users/{id}", 2)
                .then()
                .spec(responseRegressInSpecificationStatusCode200)
                .extract().as(UserData.class)
        );
        step("Check user id", () -> assertThat(response.getData().getId()).isEqualTo(2));
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
        step("Check name", () -> assertThat(response.getName()).isEqualTo("morpheus"));
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
