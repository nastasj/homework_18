package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;

public class BookStoreSpecs {
    public static RequestSpecification registrationAndLoginRequestSpec = with()
            .filter(withCustomTemplate())
            .log().all()
            .contentType(ContentType.JSON);

    public static RequestSpecification authorizedRequestSpec(String token) {
        return with()
                .filter(withCustomTemplate())
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
    }

    public static ResponseSpecification statusCode200Spec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification statusCode201Spec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();
}
