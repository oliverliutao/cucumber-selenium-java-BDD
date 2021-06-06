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
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * This class contains methods to do MOTOR portal health check
 */

public class MotorHealthCheckSteps {
    WebDriver driver;
    JsonNode testData;


    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(MotorHealthCheckSteps.class);

    public MotorHealthCheckSteps() {
        driver = Hooks.getDriver();
    }

    //    @DocStringType
//    public JsonNode json(String docString) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readTree(docString);
//    }
//
    @Given("^I prepare test data for Motor portal$")
    public void i_start_test(String docString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readTree(docString);

        log.info("Motor health check start ....testData=" + testData);
    }

    @When("^I navigate to Motor \"([^\"]*)\" and key in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {

        try {
            driver.navigate().to(url);

            waitSpinnerInvisible();

            //// page 1 ////
            WebElement makeModel = driver.findElement(By.id("make_and_model"));
            makeModel.sendKeys(testData.get("makeModelStartWith").asText());

            Actions actionProvider = new Actions(driver);
            actionProvider.moveToElement(makeModel).build().perform();
            actionProvider.moveByOffset(0, -50).build().perform();
//            Thread.sleep(500);
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            makeModel.sendKeys(Keys.ENTER);
            log.info("make and model = " + driver.findElement(By.id("tmpMakeModelCode")).getAttribute("value"));


            By yearselector = By.id("yearOfMake");
            Select selectYear = new Select(driver.findElement(yearselector));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(yearselector));
            selectYear.selectByVisibleText(testData.get("yearOfRegistration").asText());

            driver.findElement(By.id("car_registration_number")).sendKeys(testData.get("registrationNum").asText());


            driver.findElement(By.id("nric")).sendKeys(testData.get("nric").asText());

            waitSpinnerInvisible();

            driver.findElement(By.cssSelector("input[placeholder='DD']")).sendKeys(testData.get("dobDate").asText());
            driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(testData.get("dobMonth").asText());
            driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(testData.get("dobYear").asText());

            List<WebElement> allPage4Spans = driver.findElements(By.tagName("span"));
            for (WebElement e : allPage4Spans) {
                if(e.getText().equalsIgnoreCase("male")) {
                    waitSpinnerInvisible();
                    fluentWaitUtils(e);
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            By ncdselector = By.id("renewalNcd");
            Select selectNCD = new Select(driver.findElement(ncdselector));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(ncdselector));
            selectNCD.selectByVisibleText(testData.get("ncd").asText());

            List<WebElement> allClaimNo = driver.findElements(By.tagName("span"));
            for (WebElement e : allClaimNo) {
                if(e.getText().equalsIgnoreCase("0")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            By licenseSelector = By.id("drivingLicenseYear");
            Select selectLicense = new Select(driver.findElement(licenseSelector));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(licenseSelector));
            selectLicense.selectByVisibleText(testData.get("licenseYears").asText());
            log.info("driving License Year");

            driver.findElement(By.id("postalCode")).sendKeys(testData.get("postCode").asText());
            log.info("postal Code");

            //off peak car & MINDEF/TCC
            List<WebElement> allSpans = driver.findElements(By.tagName("span"));
            for (WebElement e : allSpans) {
                if(e.getText().equalsIgnoreCase("No")) {
                    waitSpinnerInvisible();
                    fluentWaitUtils(e);
//                    new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(e));
                    log.info(e.getText());
                    e.click();
//                    break;
                }
            }


            List<WebElement> allPremium = driver.findElements(By.tagName("a"));
            for (WebElement e : allPremium) {
                if(e.getText().equalsIgnoreCase("Get my premium")) {
                    fluentWaitUtils(e);
//                    new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("loadingSpinner"))));

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
            List<WebElement> allPremiumBtn = driver.findElements(By.tagName("button"));
            for (WebElement e : allPremiumBtn) {
                if(e.getText().equalsIgnoreCase("select")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 3 =============");
            //// page 3 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page3url").asText()));
            waitSpinnerInvisible();

            WebElement checkBox1=driver.findElement(By.xpath("//label[@for='driverOptionCheck']"));
            List<WebElement> checkBox1Child = checkBox1.findElements(By.xpath("./child::*"));
            for (WebElement cb : checkBox1Child) {
                String cbclass = cb.getAttribute("class");
                if(cbclass.contains("a-checkbox__label")) {
                    fluentWaitUtils(cb);
                    log.info("1st checkbox");
                    cb.click();
                    break;
                }
            }


            WebElement checkBox2=driver.findElement(By.xpath("//label[@for='excessOptionCheck']"));
            List<WebElement> checkBox2Child = checkBox2.findElements(By.xpath("./child::*"));
            for (WebElement cb : checkBox2Child) {
                String cbclass = cb.getAttribute("class");
                if(cbclass.contains("a-checkbox__label")) {
                    fluentWaitUtils(cb);
                    actionProvider.moveToElement(cb).build().perform();
                    log.info("2st checkbox");
                    cb.click();
                    break;
                }
            }


            List<WebElement> allPage3Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage3Btns) {
                if(e.getText().equalsIgnoreCase("Go to personal details")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 4 =============");
            //// page 4 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page4url").asText()));

            waitSpinnerInvisible();


            WebElement salutation = driver.findElement(By.id("salutation"));
            Select selectSL = new Select(salutation);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("salutation")));
            selectSL.selectByVisibleText(testData.get("salutation").asText());

//            driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

            driver.findElement(By.id("surname")).sendKeys(testData.get("familyName").asText());
            driver.findElement(By.id("givname")).sendKeys(testData.get("givenName").asText());

            driver.findElement(By.id("mobile_number")).sendKeys(testData.get("phoneNumber").asText());

            driver.findElement(By.id("email")).sendKeys(testData.get("email").asText());

            waitSpinnerInvisible();

            if(driver.findElement(By.id("motor_block")).getText().length() == 0) {
                driver.findElement(By.id("motor_block")).sendKeys(testData.get("block").asText());
                driver.findElement(By.id("motor_street_name")).sendKeys(testData.get("street").asText());
                driver.findElement(By.id("building_name")).sendKeys(testData.get("street").asText());
            }


            By insurerSelector = By.id("previousInsurer");
            Select selectInsurer = new Select(driver.findElement(insurerSelector));
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(insurerSelector));
            selectInsurer.selectByVisibleText(testData.get("previousInsurer").asText());


            List<WebElement> allPage4Radio = driver.findElements(By.tagName("span"));
            for (WebElement e : allPage4Radio) {
                if(e.getText().equalsIgnoreCase("No")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            List<WebElement> allPage4NextBtn = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage4NextBtn) {
                if(e.getText().equalsIgnoreCase("Go to summary and payment")) {
                    fluentWaitUtils(e);
                    log.info(e.getText());
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
//                    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(e));
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading page 6 =============");
            //// page 6 ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("page6url").asText()));

//            driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
//            waitSpinnerInvisible();

            WebElement page6Payment = driver.findElement(By.cssSelector("label[for='payment_C']"));
            fluentWaitUtils(page6Payment);
            page6Payment.click();

            List<WebElement> allPage6Btns = driver.findElements(By.tagName("a"));
            for (WebElement e : allPage6Btns) {
                if(e.getText().equalsIgnoreCase("Proceed to payment")) {
//                    Thread.sleep(10000);
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    log.info(e.getText());
                    e.click();
                    break;
                }
            }

            log.info("========= loading payment page =============");
            //// payment page ////
            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("paymentUrl").asText()));


        } catch (Exception e) {
            log.error("health check >> exception =" + e);
        }

    }


    @Then("^Motor portal reach payment page$")
    public void i_am_able_to_reach_payment_page() throws Exception {

        try{
            String currentUrl = driver.getCurrentUrl();
            log.info("current url = " + currentUrl);
            Assert.assertTrue("Motor portal NOT reach payment page, please CHECK again", currentUrl.equalsIgnoreCase(testData.get("paymentUrl").asText()));

            log.info("Motor health check end .... PASS ");
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
                .pollingEvery(Duration.ofSeconds(2));
//                .ignoring(ElementClickInterceptedException.class);
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }

    private void waitSpinnerInvisible() {

//        WebElement recalculatingSpinner = driver.findElement(By.id("recalculatingSpinner"));
        WebElement loadingSpinner = driver.findElement(By.id("loadingSpinner"));

//        if(recalculatingSpinner.isEnabled() && recalculatingSpinner.isDisplayed()) {
//            log.info("waiting recalculatingSpinner");
//            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOf(recalculatingSpinner));
//        }

        if(loadingSpinner.isEnabled() && loadingSpinner.isDisplayed()) {
            log.info("waiting loadingSpinner");
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOf(loadingSpinner));
        }
    }

}
