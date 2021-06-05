package com.test.step.defintions.healthcheck;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.step.defintions.ClickSteps;
import com.test.step.defintions.Hooks;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * This class contains methods to allow you to click on an element
 * More steps examples here: https://github.com/selenium-cucumber/selenium-cucumber-java/blob/master/doc/canned_steps.md
 */

public class HomeHealthCheckSteps {
    WebDriver driver;
    JsonNode testData;


    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(ClickSteps.class);

    public HomeHealthCheckSteps() {
        driver = Hooks.getDriver();
    }

    @DocStringType
    public JsonNode json(String docString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(docString);
    }

    @Given("^I prepare test data$")
    public void i_start_test(JsonNode json) {
        testData = json;
        log.info("Home health check start ....testData=" + testData);
    }

    @When("^I navigate to \"([^\"]*)\" and fill in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {

        try {
            driver.navigate().to(url);

//            Thread.sleep(6000);

            By spinner = By.cssSelector("div[ng-show='showSpinner']");
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));


            //// page 1 ////
            String dwellType = testData.get("dwellType").asText();
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.id("dwellingTypeRef")));
            // find element by id, must wait for element present, otherwise will hit "no such element" error
            WebElement selectDwellingType = driver.findElement(By.id("dwellingTypeRef"));
            Select selectDC = new Select(selectDwellingType);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("dwellingTypeRef")));
            selectDC.selectByVisibleText(dwellType);

            // find all buttons, then target by text "choose plan"
            List<WebElement> allbuttons = driver.findElements(By.tagName("button"));
            for (WebElement e : allbuttons) {
                log.info(e.getText());
                if(e.getText().equalsIgnoreCase("choose plan")) {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 2 =============");

            //// page 2 ////
            new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(testData.get("page2url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tbody")));
            List<WebElement> allPremiumBtn = driver.findElements(By.tagName("a"));
            for (WebElement e : allPremiumBtn) {
                log.info(e.getText());
                if(e.getText().equalsIgnoreCase("select")) {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 3 =============");
            //// page 3 ////
            new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(testData.get("page3url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            List<WebElement> allPage3Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage3Btns) {
                log.info(e.getText());
                if(e.getText().equalsIgnoreCase("go to personal details")) {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 4 =============");
            //// page 4 ////
            new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(testData.get("page4url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));




//            Thread.sleep(20000);

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
//        Thread.sleep(10000);
        driver.quit();
    }

}
