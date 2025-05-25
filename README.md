## Automation Framework
    This is a Cucumber framework with a Maven build and JUnit 5 test runner.
### Observation
       1) Delete endpoint returns 201 status code
       2) Invalid request POST request returns 500 status code
       3) Invalid token and no token pass DELETE request returns 403

    **To Run the test cases added validation that accepts this status code based on the application's behavior**.
### Required software/plugins
     * java - 11
     * Maven - 3.9
     * Cucumber -7+
     * Jenkins - 2.462(CI Integration)
### How to run tests
    * commandline(project folder):
         mvn clean test.

    * To run Specific test case based on tag:
        for ex:- mvn clean test -D cucumber.filter.tags="@booker"
  
     All used tag names can be found in -"src/test/resources/features/booker.feature"

    * Test Runner Configuration file path:
           src/test/java/org/booker/api/runner/RunCucumberTest.java
### Reports
     After executions,find the Cucumber html report in path  -"target/cucumber.html"
     Sample generated reports available in path -"src/test/resources/cucumber.html"
### CI Integration
    I have tested CI in  my local host ,so added the jenkinsfile in path "root/Jenkinsfile"

    * Required plugin:
         Git,Git Client,Pipeline API ,Cucumber Reports(try to add default suggested plugin if any)
    * Set up :
         Create pipeline project and Configure  "PipeLine script from SCM"
    * Reports:
         Cucumber HTML reports will appear on the left side of the Jenkins build page under "Cucumber Reports"
### Flows,validations and features
        * POST,GET,PUT,DELETE Requests.
        * Response body validation with Request body.
        * Negative validations such as invalid request body and no authorization
        * Response body schema validation.
        * Response code validation.
        * Response body not null validation.
        * Updating and deleting key from request body.
  




