package org.booker.api.booker.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.booker.api.booker.utils.Utility;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Validator {

    public  void assertStatusCode(Response response, int expectedStatusCode) {
        assertThat("Status code mismatch", response.getStatusCode(), equalTo(expectedStatusCode));
    }
    public void validateSchema(Response response,String schemaFile)
    {
        assertThat(response.asString(), matchesJsonSchemaInClasspath(schemaFile));

    }
    public  void validateResponseWithRequestData(Response response, Map<String, Object> expectedData) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseData = response.as(Map.class);
        responseData= Utility.removeBookingIDAndObject(responseData);

        for (Map.Entry<String, Object> entry : expectedData.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            assertThat("Mismatch for key: " + key,
                    responseData.get(key), equalTo(expectedValue));
        }
    }
    public  void assertListNotNull(Response response) {
        String responseBody = response.asString();
        assertThat("Response array should not be empty", response.jsonPath().getList("$"), not(empty()));
    }

}
