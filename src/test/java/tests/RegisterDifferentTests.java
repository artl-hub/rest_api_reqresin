package tests;


import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.RegisterSpec.*;

@DisplayName("API TESTS")
@Tag("testSet")
public class RegisterDifferentTests extends TestBase {


    @Test
    @Tag("positive")
    @DisplayName("REGISTER - SUCCESSFUL")

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

            assertNotNull(response.getToken(), "Token should not be null");
            assertEquals("4", response.getId());
        });
    }


    @Test
    @Tag("negative")
    @DisplayName("MISSING PASSWORD")
    void missingPasswordTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");


        ErrorRegisterModel response = step("Make request", () ->
        given(registerRequestSpec)
                .body(registerData)

        .when()
                .post()

        .then()
                .spec(missingAuthorizationElements)
                .extract().as(ErrorRegisterModel.class));

        step("Check response", ()->
                assertEquals("Missing password", response.getError()));

    }


    @Test
    @Tag("negative")
    @DisplayName("MISSING PASSWORD AND EMAIL")
    void missingPasswordAndEmailTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();

        ErrorRegisterModel response = step("Make request", () ->
                given(registerRequestSpec)
                        .body(registerData)

                .when()
                        .post()

                .then()
                        .spec(missingAuthorizationElements)
                        .extract().as(ErrorRegisterModel.class));

        step("Check response", ()->
                assertEquals("Missing email or username", response.getError()));

    }

    @Test
    @Tag("negative")
    @DisplayName("MISSING SYMBOL IN EMAIL: @ ")
    void missingAtSymbolInEmailTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holtreqres.in");
        registerData.setPassword("pistol");


        ErrorRegisterModel response = step("Make request", () ->
                given(registerRequestSpec)
                        .body(registerData)

                .when()
                        .post()

                .then()
                        .spec(missingAuthorizationElements)
                        .extract().as(ErrorRegisterModel.class));

        step("Check response", ()->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

}



