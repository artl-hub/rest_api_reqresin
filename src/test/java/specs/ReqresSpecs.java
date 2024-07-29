package ru.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static ru.reqres.helpers.CustomAllureListener.withCustomTemplates;

public class ReqresSpecs {
  public static RequestSpecification basicRequestSpec = with()
    .filter(withCustomTemplates())
    .log().all();
  
  public static ResponseSpecification newUserResponseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .log(BODY)
    .build();
  
  public static ResponseSpecification notFound404ResponseSpec = new ResponseSpecBuilder()
    .expectStatusCode(404)
    .log(STATUS)
    .build();
  
  public static ResponseSpecification loggingResponseSpec(int statusCode) {
    return new ResponseSpecBuilder()
      .expectStatusCode(statusCode)
      .log(STATUS)
      .log(BODY)
      .build();
  }
}
