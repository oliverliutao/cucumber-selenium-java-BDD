package com.test.step.defintions.healthcheck;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.step.defintions.Hooks;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

            waitSpinnerInvisible();
            Thread.sleep(3000);

            Actions actionProvider = new Actions(driver);


            //// page 1 ////
            log.info("========= page 1 loaded =============");
            List<WebElement> allSpans = driver.findElements(By.tagName("span"));
            for (WebElement e : allSpans) {
                if(e.getText().equalsIgnoreCase("Annual")) {
                    fluentWaitUtils(e);
//                    Thread.sleep(6000);
                    log.info(e.getText());
                    actionProvider.moveToElement(e).build().perform();
                    e.click();
                    break;
                }
            }

            // find all buttons, then target by text "choose plan"
            List<WebElement> allbuttons = driver.findElements(By.tagName("button"));
            for (WebElement e : allbuttons) {
                if(e.getText().equalsIgnoreCase("choose plan")) {
                    fluentWaitUtils(e);
//                    Thread.sleep(6000);
                    actionProvider.moveToElement(e).build().perform();
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 2 =============");

            //// page 2 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page2url").asText()));
            waitSpinnerInvisible();
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tbody")));
            List<WebElement> allPremiumBtn = driver.findElements(By.tagName("a"));
            for (WebElement e : allPremiumBtn) {
                if(e.getText().equalsIgnoreCase("select")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    actionProvider.moveToElement(e).build().perform();
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 3 =============");
            //// page 3 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page3url").asText()));
            waitSpinnerInvisible();
            List<WebElement> allPage3Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage3Btns) {
                if(e.getText().equalsIgnoreCase("go to personal details")) {
                    fluentWaitUtils(e);
//                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//                    Thread.sleep(5000);
                    actionProvider.moveToElement(e).build().perform();
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 4 =============");
            //// page 4 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page4url").asText()));
            waitSpinnerInvisible();

            driver.findElement(By.name("identificationNo")).sendKeys(testData.get("nric").asText());
            waitSpinnerInvisible();

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
                    waitSpinnerInvisible();
                    fluentWaitUtils(e);
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
                    actionProvider.moveToElement(e).build().perform();
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }


            driver.findElement(By.id("mobile")).sendKeys(testData.get("phoneNumber").asText());

            driver.findElement(By.id("email")).sendKeys(testData.get("email").asText());

            waitSpinnerInvisible();

            driver.findElement(By.id("postalCode")).sendKeys(testData.get("postCode").asText());

            List<WebElement> allPage4TagA = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage4TagA) {
                if(e.getText().equalsIgnoreCase("Find my address")) {
                    waitSpinnerInvisible();
                    fluentWaitUtils(e);
                    Thread.sleep(5000);
                    actionProvider.moveToElement(e).build().perform();
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            waitSpinnerInvisible();

            if(driver.findElement(By.id("addressLine1")).getText().length() == 0) {
                driver.findElement(By.id("addressLine1")).sendKeys(testData.get("block").asText());
                driver.findElement(By.id("addressLine2")).sendKeys(testData.get("street").asText());
                driver.findElement(By.id("addressLine4")).sendKeys(testData.get("street").asText());
            }

            Thread.sleep(5000);

            List<WebElement> allPage4Btns = driver.findElements(By.tagName("button"));
            for (WebElement e : allPage4Btns) {
                if(e.getText().equalsIgnoreCase("Go to summary & payment")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    actionProvider.moveToElement(e).build().perform();
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 5 =============");
            //// page 5 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page5url").asText()));

            waitSpinnerInvisible();

            List<WebElement> allPage5Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage5Btns) {
                if(e.getText().equalsIgnoreCase("I agree - buy now")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    actionProvider.moveToElement(e).build().perform();
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 6 =============");
            //// page 6 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page6url").asText()));
            waitSpinnerInvisible();

            List<WebElement> allPage6H3 = driver.findElements(By.tagName("h3"));
            for (WebElement e : allPage6H3) {
                if(e.getText().equalsIgnoreCase("Pay with Visa or MasterCard")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            List<WebElement> allPage6Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage6Btns) {
                if(e.getText().equalsIgnoreCase("Proceed to payment")) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView();", e);
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    Thread.sleep(6000);
                    log.info(e.getText());
                    actionProvider.moveToElement(e).build().perform();
                    e.click();
                }
            }

            Thread.sleep(6000);

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

    private void waitSpinnerInvisible() {

        By spinner = By.cssSelector("div[ng-show='showSpinner']");
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
    }

}
