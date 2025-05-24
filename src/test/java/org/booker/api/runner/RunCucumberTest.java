package org.booker.api.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key=FEATURES_PROPERTY_NAME, value="src/test/resources/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.booker.api.stepdefinitions") // Adjust to your stepdef package
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber.html, json:target/cucumber.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@smokes")

public class RunCucumberTest {

}
