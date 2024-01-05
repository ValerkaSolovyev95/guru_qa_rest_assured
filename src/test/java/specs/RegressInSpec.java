package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class RegressInSpec {
    public static RequestSpecification requestRegressInSpecification = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().method()
            .log().body()
            .log().uri();

    public static ResponseSpecification responseRegressInSpecificationStatusCode200 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification responseRegressInSpecificationStatusCode201 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification responseRegressInSpecificationStatusCode204 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification responseRegressInSpecificationStatusCode404 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(404)
            .build();
}
