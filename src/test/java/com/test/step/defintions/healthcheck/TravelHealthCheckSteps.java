package com.test.step.defintions.healthcheck;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.step.defintions.Hooks;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


/**
 * This class contains methods to do TRAVEL portal health check
 */

public class TravelHealthCheckSteps {
    WebDriver driver;
    JsonNode testData;


    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(TravelHealthCheckSteps.class);

    public TravelHealthCheckSteps() {
        driver = Hooks.getDriver();
    }

//    @DocStringType
//    public JsonNode json(String docString) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readTree(docString);
//    }
//
    @Given("^I prepare test data for Travel portal$")
    public void i_start_test(String docString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readTree(docString);

        log.info("Travel health check start ....testData=" + testData);
    }

    @When("^I navigate to Travel \"([^\"]*)\" and key in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {

        try {
            driver.navigate().to(url);


            By spinner = By.cssSelector("div[ng-show='showSpinner']");
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));


            //// page 1 ////
            List<WebElement> allSpans = driver.findElements(By.tagName("span"));
            for (WebElement e : allSpans) {
                if(e.getText().equalsIgnoreCase("Annual")) {
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            // find all buttons, then target by text "choose plan"
            List<WebElement> allbuttons = driver.findElements(By.tagName("button"));
            for (WebElement e : allbuttons) {
                if(e.getText().equalsIgnoreCase("choose plan")) {
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading page 2 =============");

            //// page 2 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page2url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tbody")));
            List<WebElement> allPremiumBtn = driver.findElements(By.tagName("a"));
            for (WebElement e : allPremiumBtn) {
                if(e.getText().equalsIgnoreCase("select")) {
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading page 3 =============");
            //// page 3 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page3url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            List<WebElement> allPage3Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage3Btns) {
                if(e.getText().equalsIgnoreCase("go to personal details")) {
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading page 4 =============");
            //// page 4 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page4url").asText()));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            driver.findElement(By.name("identificationNo")).sendKeys(testData.get("nric").asText());
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            WebElement salutation = driver.findElement(By.id("salutation"));
            Select selectSL = new Select(salutation);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("salutation")));
            selectSL.selectByVisibleText(testData.get("salutation").asText());


            driver.findElement(By.name("familyName")).sendKeys(testData.get("familyName").asText());
            driver.findElement(By.name("givenName")).sendKeys(testData.get("givenName").asText());

            driver.findElement(By.cssSelector("input[placeholder='DD']")).sendKeys(testData.get("dobDate").asText());
            driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(testData.get("dobMonth").asText());
            driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(testData.get("dobYear").asText());


            List<WebElement> allPage4Spans = driver.findElements(By.tagName("span"));
            for (WebElement e : allPage4Spans) {
                if(e.getText().equalsIgnoreCase("male")) {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }


            driver.findElement(By.id("mobile")).sendKeys(testData.get("phoneNumber").asText());

            driver.findElement(By.id("email")).sendKeys(testData.get("email").asText());

            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            driver.findElement(By.id("postalCode")).sendKeys(testData.get("postCode").asText());

            List<WebElement> allPage4TagA = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage4TagA) {
                if(e.getText().equalsIgnoreCase("Find my address")) {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            if(driver.findElement(By.id("addressLine1")).getText().length() == 0) {
                driver.findElement(By.id("addressLine1")).sendKeys(testData.get("block").asText());
                driver.findElement(By.id("addressLine2")).sendKeys(testData.get("street").asText());
                driver.findElement(By.id("addressLine4")).sendKeys(testData.get("street").asText());
            }


            List<WebElement> allPage4Btns = driver.findElements(By.tagName("button"));
            for (WebElement e : allPage4Btns) {
                if(e.getText().equalsIgnoreCase("Go to summary & payment")) {
                    fluentWaitUtils(e);
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading page 5 =============");
            //// page 5 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page5url").asText()));
            new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            List<WebElement> allPage5Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage5Btns) {
                if(e.getText().equalsIgnoreCase("I agree - buy now")) {
                    fluentWaitUtils(e);
//                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading page 6 =============");
            //// page 5 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page6url").asText()));
            new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(spinner));

            List<WebElement> allPage6H3 = driver.findElements(By.tagName("h3"));
            for (WebElement e : allPage6H3) {
                if(e.getText().equalsIgnoreCase("Pay with Visa or MasterCard")) {
                    fluentWaitUtils(e);
//                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

//            new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            List<WebElement> allPage6Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage6Btns) {
                if(e.getText().equalsIgnoreCase("Proceed to payment")) {

                    Thread.sleep(10000);
//                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    log.info(e.getText());
                    break;
                }
            }

            log.info("========= loading payment page =============");
            //// payment page ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("paymentUrl").asText()));

//            Thread.sleep(200000);

        } catch (Exception e) {
            log.error("health check >> exception =" + e);
        }

    }


    @Then("^Travel portal reach payment page$")
    public void i_am_able_to_reach_payment_page() throws Exception {

        try{
            String currentUrl = driver.getCurrentUrl();
            log.info("current url = " + currentUrl);
            Assert.assertTrue("Travel portal NOT reach payment page, please CHECK again", currentUrl.equalsIgnoreCase(testData.get("paymentUrl").asText()));

            log.info("Travel health check end .... PASS ");
            //        Thread.sleep(10000);

            driver.quit();
        }catch (Exception e) {
            log.error("check payment page >> exception =" + e);
        }finally {
            driver.quit();
        }
    }

    private void fluentWaitUtils(WebElement e){

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(ElementClickInterceptedException.class);
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }

}
