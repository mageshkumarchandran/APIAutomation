package org.booker.api.booker.requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.booker.api.booker.interfaces.ApiFlows;
import org.booker.api.booker.utils.Utility;
import java.util.HashMap;
import java.util.Map;

public class BookerRequests implements ApiFlows {
    private final RequestSpecification requestSpec;
    public BookerRequests() {
        RestAssured.baseURI = Utility.getProperty("base.url");
        RestAssured.basePath = Utility.getProperty("path");
        requestSpec = RestAssured
                .given()
                .contentType("application/json")
                .accept("application/json");
    }

    @Override
    public Response get(String id) {
        String actualEndPoint=Utility.getProperty("base.url")+Utility.getProperty("path");
        if(id!=null && !id.isEmpty())
            actualEndPoint=actualEndPoint+"/"+id;
        return requestSpec
                .when()
                .get(actualEndPoint)
                .then()
                .log().all()
                .extract().response();
    }
    @Override
    public Response post(Object payload) {
        return requestSpec
                .body(payload)
                .when()
                .post()
                .then()
                .extract().response();
    }

    @Override
    public Response put(String id, Object payload,String token) {
        return requestSpec
                .cookie("token",token)
                .body(payload)
                .when()
                .put(id)
                .then()
                .extract().response();
    }
    @Override
    public Response delete(String id,String token) {
        if(token!=null && !id.isEmpty() )
            requestSpec.cookie("token",token);
        return requestSpec
                .when()
                .delete(Utility.getProperty("base.url")+Utility.getProperty("path")+"/"+id)
                .then()
                .log().cookies()
                .extract().response();
    }

    @Override
    public Response generateToken() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", Utility.getProperty("auth.username"));
        credentials.put("password", Utility.getProperty("auth.password"));
        return requestSpec
                .body(credentials)
                .when()
                .post(Utility.getProperty("base.url")+Utility.getProperty("auth.path"))
                .then()
                .extract().response();
    }
}
