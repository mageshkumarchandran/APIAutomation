@booker
Feature: Booker API End point validations

  @test_1 @post
  Scenario: Trigger booker POST request and validate response
    Given Read the request json file
    When Trigger a "POST" request.
    Then Verify the status code is 200
    And Verify the response with request body

  @test_2 @put
  Scenario: Trigger booker PUT request and validate response
    Given Read the request json file
    When Update the fields in the request body
      | key        | value |
      | firstname  | test  |
      | totalprice | 190   |
    And Update the "checkin" date in request body
    When Trigger a "put" request with id "26"
    Then Verify the status code is 200
    And Verify the response with request body

  @test_3 @delete
  Scenario: Trigger booker DELETE request and validate deletion
    When Trigger a "delete" request with id "95"
    Then Verify the status code is 201
    #This step is to verify record is deleted
    When Trigger a "get" request with id "95"
    Then Verify the status code is 404

  @test_4 @get
  Scenario: Trigger booker GET request and validate response is not null
    When Trigger a "GET" request.
    Then Verify the status code is 200
    And Verify the response is not empty

  @test_5 @get_id
  Scenario: Trigger booker GET request by id and validate response schema
    When Trigger a "GET" request with id "47"
    Then Verify the status code is 200
    And Verify the "firstname" field is not null in the response
    And Verify the "totalprice" field is not null in the response
    And Verify the response Schema

  @test_6 @negative @invalidRequest
  Scenario: Trigger booker invalid POST request and validate status code
    Given Read the request json file
    When Delete "firstname" from request file
    When Trigger a "invalidPOST" request.
    Then Verify the status code is 500

  @test_7 @negative @invalidToken
  Scenario: Trigger booker invalid token DELETE request and validate status code
    When Trigger a "invalidTokenDELETE" request.
    Then Verify the status code is 403

  @test_8 @negative @noToken
  Scenario: Trigger booker invalid token DELETE request and validate status code
    When Trigger a "noTokenDELETE" request.
    Then Verify the status code is 404