package org.booker.api.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.booker.api.booker.requests.BookerRequests;
import org.booker.api.booker.requests.Validator;
import org.booker.api.booker.utils.Utility;
import java.io.IOException;
import java.util.List;
import java.util.Map;
public class BookerSteps {
    private final Utility utility;
    private final BookerRequests bookerRequests;
    private final Validator validator;
    private Map<String, Object> requestData;
    private Response responseBody;

    public BookerSteps(Utility utility, BookerRequests bookerRequests, Validator validator) {
        this.utility = utility;
        this.bookerRequests = bookerRequests;
        this.validator = validator;
    }
    @Given("Read the request json file")
    public void read_the_request_json_file() throws IOException {
        requestData = utility.returnJsonAsMap();
    }
    @When("Trigger a {string} request")
    public void trigger_a_request(String type) throws JsonProcessingException {
        if (type.equalsIgnoreCase("post"))
            responseBody = bookerRequests.post(requestData);
        else if (type.equalsIgnoreCase("invalidpost"))
            responseBody = bookerRequests.post(requestData);

        else if (type.contains("invalidToken"))
            responseBody = bookerRequests.delete("7", "invalid");
        else if (type.contains("noToken"))
            responseBody = bookerRequests.delete("9", "");
        else
            responseBody = bookerRequests.get("");
    }
    @When("Trigger a {string} request with id {string}")
    public void trigger_a_request_with(String type, String id) {
        String token = utility.getAuthToken(bookerRequests.generateToken());
        if (type.equalsIgnoreCase("put"))
            responseBody = bookerRequests.put(id, requestData, token);
        else if (type.equalsIgnoreCase("get"))
            responseBody = bookerRequests.get(id);
        else
            responseBody = bookerRequests.delete(id, token);

    }
    @When("Trigger a delete request with id {int}")
    public void trigger_a_delete_request_with(String id) {
        String token = utility.getAuthToken(bookerRequests.generateToken());
        responseBody = bookerRequests.delete(id, token);
    }
    @Then("Verify the status code is {int}")
    public void verify_the_status_code_is(Integer statusCode) {

        validator.assertStatusCode(responseBody, statusCode);
    }

    @Then("Verify the response header")
    public void verify_the_response_header() {
    }

    @Then("Verify the response with request body")
    public void verify_the_response_with_request_body() {
        validator.validateResponseWithRequestData(responseBody, requestData);
    }

    @Then("Verify the response Schema")
    public void verify_the_response_schema() {
        String file = Utility.getProperty("schema");
        validator.validateSchema(responseBody, file);
    }

    @Then("Verify the response is not empty")
    public void verify_the_response_not_null() {
        validator.assertListNotNull(responseBody);
    }

    @When("Update the fields in the request body")
    public void update_the_field_in_the_request_body_to(DataTable dataTable) throws JsonProcessingException {
        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> value : values) {
            requestData = utility.updateRecordInBookingReq(requestData, value.get("key"), value.get("value"));
        }
    }

    @Then("Verify the {string} field is not null in the response")
    public void verify_the_field_is_not_null_in_the_response(String key) {
        validator.assertKeyNotNull(responseBody,key);
    }


    @Given("Retrieve all the booking details")
    public void retrieve_all_the_booking_details() {
        responseBody = bookerRequests.get("");
    }

    @When("Update the {string} date in request body")
    public void update_date_in_file(String dateType) {
        utility.updateDateInBookingReq(requestData, dateType);
    }

    @When("Delete {string} from request file")
    public void delete_a_key_from_Request(String key) throws JsonProcessingException {
        requestData = utility.deleteRecordInBookingReq(requestData, key);
    }
}
