package com.test.step.defintions;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@pa",
        plugin = {"html:target/cucumberHtmlReport"},
        features = "classpath:features"

)

public class RunCukesTest {
}
