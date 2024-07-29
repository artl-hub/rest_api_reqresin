package tests;


import models.UserDataResponseModel;
import models.UsersListResponseModel;
import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.RegisterSpec.*;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpecs.*;


public class UserManagementTests extends TestBase {


    @Test
    @Tag("positive")
    @DisplayName("REGISTER - SUCCESSFUL")
//------------------------1--------------------------
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


    //------------------------2--------------------------
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


    //------------------------3-------------------------- added chema
    @Test
    void singleUserIsDisplayedTest() {

        UserDataResponseModel response = step("Отправляем запрос", () ->
                given(basicRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(loggingResponseSpec(200))
                        .body(matchesJsonSchemaInClasspath("schemas/success-single-user-schema.json"))
                        .extract().as(UserDataResponseModel.class));

        step("Проверяем ответ", () -> {
            assertThat(response.getUser().getId()).isEqualTo(2);
            assertThat(response.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.getUser().getFirstName()).isEqualTo("Janet");
            assertThat(response.getUser().getLastName()).isEqualTo("Weaver");
        });
    }

    //------------------------4--------------------------
    @Test
    void userListIsDisplayedTest() {
        UsersListResponseModel response = step("Отправляем запрос", () ->
                given(basicRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
//                        .body(matchesJsonSchemaInClasspath("schemas/success-users-list-schema.json"))
                        .spec(loggingResponseSpec(200))
                        .extract().as(UsersListResponseModel.class));

        step("Проверяем ответ", () ->
                assertThat(response.getPage()).isEqualTo(2));
    }

    //------------------------5--------------------------





    @Test
    @Tag("delete")
    @DisplayName("DELETE - SUCCESSFUL")
    void deleteUserTest() {
        var response = step("Отправляем запрос DELETE к /api/users/2", () ->
                given()
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteResponseSpec)
                        .extract().response()
        );

        step("Проверяем ответ", () ->
                assertThat(response.statusCode()).isEqualTo(204));
    }
}











