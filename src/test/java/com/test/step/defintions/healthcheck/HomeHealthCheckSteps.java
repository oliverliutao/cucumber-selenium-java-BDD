package com.test.step.defintions.healthcheck;

import com.selenium.configure.environment.PropertiesHandler;
import com.test.step.defintions.ClickSteps;
import com.test.step.defintions.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DocStringType;

import java.io.IOException;

/**
 * This class contains methods to allow you to click on an element
 * More steps examples here: https://github.com/selenium-cucumber/selenium-cucumber-java/blob/master/doc/canned_steps.md
 */

public class HomeHealthCheckSteps {
    WebDriver driver;
    JsonNode testData;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(ClickSteps.class);

    public HomeHealthCheckSteps() {
        driver = Hooks.getDriver();
    }

    @DocStringType
    public JsonNode json(String docString) throws IOException {
        return objectMapper.readTree(docString);
    }

    @Given("^I prepare test data$")
    public void i_start_test(JsonNode json) {
        testData = json;
        log.info("Home health check start ....");
    }

    @When("^I navigate to \"([^\"]*)\" and fill in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {

        try {
            driver.navigate().to(url);

//            Thread.sleep(10000);

            WebElement selectDwellingType = driver.findElement(By.id("dwellingTypeRef"));
            Select selectDC = new Select(selectDwellingType);
            selectDC.selectByVisibleText("Detached");


        } catch (Exception e) {
            log.error("exception =" + e);
        }
//        finally {
//            driver.quit();
//        }

    }


    @Then("^I am able to reach payment page$")
    public void i_am_able_to_reach_payment_page() throws Exception {

        log.info("Home health check end ....");
        Thread.sleep(10000);
        driver.quit();
    }

}
