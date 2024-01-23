package tests;

import models.RegisterBodyModel;
import models.ResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTests {

//"{\n" +
//                "    \"email\": \"eve.holt@reqres.in\",\n" +
//                "    \"password\": \"pistol\"\n" +
//                "}";


    @Test
    void successfulRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseModel response = given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

        .when()
                .post("https://reqres.in/api/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        assertEquals("4", response.getId());



//        @Test
//        void BadPracticeSuccessfulRegisterTest() {
//            String regData = "{\n" +
//                    "    \"email\": \"eve.holt@reqres.in\",\n" +
//                    "    \"password\": \"pistol\"\n" +
//                    "}";
//            given()
//                    .body(regData)
//                    .contentType(JSON)
//                    .log().uri()
//
//            .when()
//                    .post("https://reqres.in/api/register")
//
//            .then()
//                    .log().status()
//                    .log().body()
//                    .statusCode(200)
//                    .body("id", is(4),
//                            "token", is("QpwL5tke4Pnpja7X4"));
//        }


    }
}
