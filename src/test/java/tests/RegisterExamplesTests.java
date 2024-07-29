package tests;


import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.RegisterBodyLombokModel;
import models.lombok.ResponseLombokModel;
import models.pojo.RegisterBodyModel;
import models.pojo.ResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static helpers.CustomAllureListener.withCustomTemplates;
import static specs.RegisterSpec.registerRequestSpec;
import static specs.RegisterSpec.registerResponseSpec;

@Disabled
public class RegisterExamplesTests {


    @Test
    void successfulRegisterPojoTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseModel response = given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        assertEquals("4", response.getId());

    }


    @Test
    void successfulRegisterLombokTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseLombokModel response = given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        assertEquals("4", response.getId());
    }


    @Test
    void successfulRegisterAllureTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().body()
                .log().headers()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        assertEquals("4", response.getId());
    }


    @Test
    void successfulRegisterСustomAllureTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        assertEquals("4", response.getId());
    }

    @Test
    void successfulRegisterWithStepsTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");


        ResponseLombokModel response = step("Make request", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().body()
                        .log().headers()
                        .body(registerData)
                        .contentType(JSON)
                        .log().uri()

                        .when()
                        .post("https://reqres.in/api/register")

                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(ResponseLombokModel.class));


        step("Check request", () -> {

            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals("4", response.getId());
        });
    }

    @Test
    void successfulRegisterWithSpecsTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");


        ResponseLombokModel response = step("Make request", () ->
                given(registerRequestSpec)
                        .body(registerData)

                .when()
                        .post()

                .then()
                        .spec(registerResponseSpec)
                        .extract().as(ResponseLombokModel.class));


        step("Check request", () -> {

            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals("4", response.getId());
        });
    }

}

