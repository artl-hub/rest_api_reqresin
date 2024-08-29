package tests;


import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.RegisterSpec.*;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpecs.*;


public class UserManagementTests extends TestBase {


    @Test
    @Tag("positive")
    @Feature("User Management")
    @Story("New User Registration")
    @DisplayName("Register - Successful")

    void successfulRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterSuccessfulResponseModel response = step("Make registration request", () ->
                given(registerRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/successful-register-schema.json"))
                        .extract().as(RegisterSuccessfulResponseModel.class));

        step("Validate registration response", () -> {

            assertNotNull(response.getToken(), "Token should not be null");
            assertEquals("4", response.getId());
        });
    }

    @Test
    @Tag("negative")
    @Feature("User Management")
    @Story("New User Registration")
    @DisplayName("Registration Error: Password is Missing")
    void missingPasswordTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");


        RegisterErrorModel response = step("Make registration request without password", () ->
                given(registerRequestSpec)
                        .body(registerData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(missingAuthorizationElements)
                        .extract().as(RegisterErrorModel.class));

        step("Validate error message in response", ()->
                assertEquals("Missing password", response.getError()));

    }

    @Test
    @Tag("positive")
    @Feature("User Management")
    @Story("Getting Single User Details")
    @DisplayName("Single User is Displayed")
    void singleUserIsDisplayedTest() {

        UserDataResponseModel response = step("Send request to get single user", () ->
                given(basicRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(loggingResponseSpec(200))
                        .body(matchesJsonSchemaInClasspath("schemas/success-single-user-schema.json"))
                        .extract().as(UserDataResponseModel.class));

        step("Verify response data", () -> {
            assertThat(response.getUser().getId()).isEqualTo(2);
            assertThat(response.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.getUser().getFirstName()).isEqualTo("Janet");
            assertThat(response.getUser().getLastName()).isEqualTo("Weaver");
        });
    }

    @Test
    @Tag("positive")
    @Feature("User Management")
    @Story("Getting User List ")
    @DisplayName("User List is Displayed")
    void userListIsDisplayedTest() {
        UsersListResponseModel response = step("Send request to get user list", () ->
                given(basicRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemas/user-list-is-displayed-test-schema.json"))
                        .spec(loggingResponseSpec(200))
                        .extract().as(UsersListResponseModel.class));

        step("Verify response data", () ->
                assertThat(response.getPage()).isEqualTo(2));
    }

    @Test
    @Tag("delete")
    @Feature("User Management")
    @Story("Delete Single User ")
    @DisplayName("Delete - Successful")
    void deleteUserTest() {
        var response = step("Send DELETE request to /api/users/2", () ->
                given()
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteResponseSpec)
                        .extract().response()
        );

        step("Verify that response status code is 204", () ->
                assertThat(response.statusCode()).isEqualTo(204));
    }
}











