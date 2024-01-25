package tests;


import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.*;
import models.pojo.RegisterBodyModel;
import models.pojo.ResponseModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegisterSpec.*;



public class RegisterDifferentTests {

//"{\n" +
//                "    \"email\": \"eve.holt@reqres.in\",\n" +
//                "    \"password\": \"pistol\"\n" +
//                "}";

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


    @Test
    void missingPasswordTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");


        MissingPasswordModel response = step("Make request", () ->
        given(registerRequestSpec)
                .body(registerData)

        .when()
                .post()

        .then()
                .spec(missingPasswordResponseSpec)
                .extract().as(MissingPasswordModel.class));

        step("Check response", ()->
                assertEquals("Missing password", response.getError()));

    }

    @Test
    void missingPasswordAndEmailTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();

        MissingPasswordAndEmailModel response = step("Make request", () ->
                given(registerRequestSpec)
                        .body(registerData)

                .when()
                        .post()

                .then()
                        .spec(missingPasswordAndEmailResponseSpec)
                        .extract().as(MissingPasswordAndEmailModel.class));

        step("Check response", ()->
                assertEquals("Missing email or username", response.getError()));

    }

    @Test
    void missingAtSymbolInEmailTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holtreqres.in");
        registerData.setPassword("pistol");


        MissingAtSymbolInEmailModel response = step("Make request", () ->
                given(registerRequestSpec)
                        .body(registerData)

                        .when()
                        .post()

                        .then()
                        .spec(missingAtSymbolInEmailResponseSpec)
                        .extract().as(MissingAtSymbolInEmailModel.class));


        step("Check response", ()->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }



}



