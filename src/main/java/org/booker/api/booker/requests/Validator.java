package org.booker.api.booker.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.booker.api.booker.utils.Utility;
import java.util.Map;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Validator {

    /**  This function checks the status code is equal */
    public  void assertStatusCode(Response response, int expectedStatusCode) {
        assertThat("Status code mismatch", response.getStatusCode(), equalTo(expectedStatusCode));
    }
    /**  This function perform schema validation  */
    public void validateSchema(Response response,String schemaFile)
    {
        assertThat(response.asString(), matchesJsonSchemaInClasspath(schemaFile));

    }
    /**  This function checks the passed json key is not null  */
    public  void assertKeyNotNull(Response response, String key) {
        assertThat("Field is null", response.jsonPath().get(key), notNullValue());
    }
    /**  This function validate request and response body record match  */
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
    /**  This function verify list in the response body is not empty  */
    public  void assertListNotNull(Response response) {
        assertThat("Response array should not be empty", response.jsonPath().getList("$"), not(empty()));
    }

}