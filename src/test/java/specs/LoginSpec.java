package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class LoginSpec {
    public static RequestSpecification loginRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .basePath("/api/login")
            .log().method()
            .log().body()
            .log().uri();

    public static ResponseSpecification loginResponseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();
}
