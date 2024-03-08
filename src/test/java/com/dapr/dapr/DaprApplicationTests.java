package com.dapr.dapr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.junit.jupiter.Testcontainers;


import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

@SpringBootTest(classes=CustomerCatalogTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Testcontainers
class DaprApplicationTests {

    @Test
    void storeAndRetrieveCustomerTest() {
        String customerId = UUID.randomUUID().toString();
        with()
                .body(new DaprApplication.Customer(customerId, "nabil", "nabil@mail.com"))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/")
                .then()
                .assertThat().statusCode(200);


    }

}
