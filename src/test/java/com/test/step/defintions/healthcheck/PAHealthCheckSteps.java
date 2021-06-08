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
 * This class contains methods to do PA portal health check
 */


public class PAHealthCheckSteps {

    WebDriver driver;
    JsonNode testData;


    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(PAHealthCheckSteps.class);

    public PAHealthCheckSteps() {
        driver = Hooks.getDriver();
    }


    @Given("^I prepare test data for PA portal$")
    public void i_start_test(String docString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readTree(docString);

        log.info("PA health check start ....testData=" + testData);
    }


    @When("^I navigate to PA \"([^\"]*)\" and key in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {

        try {
            driver.navigate().to(url);

            Actions actionProvider = new Actions(driver);
            JavascriptExecutor executor = (JavascriptExecutor) driver;

            //// page 1 ////
            log.info("========= page 1 loaded =============");
            driver.findElement(By.cssSelector("input[type='radio']")).click();

            driver.findElement(By.cssSelector("img[src='library/images/buttons/btn-continue.gif']")).click();

            log.info("========= loading page 2 =============");

            By salutSelector = By.name("salutation");
            WebElement salutation = driver.findElement(salutSelector);
            Select selectSL = new Select(salutation);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(salutSelector));
            selectSL.selectByVisibleText(testData.get("salutation").asText());
            log.info("salutation");

            driver.findElement(By.name("surname")).sendKeys(testData.get("familyName").asText());
            driver.findElement(By.name("givenname")).sendKeys(testData.get("givenName").asText());
            log.info("name");

            driver.findElement(By.name("identificationNo")).sendKeys(testData.get("nric").asText());
            log.info("nric");

            By occSelector = By.name("occupationClass");
            WebElement occupation = driver.findElement(occSelector);
            Select selectOCC = new Select(occupation);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(occSelector));
            selectOCC.selectByVisibleText(testData.get("class1").asText());
            log.info("occupation class");

            driver.findElement(By.name("contactMobile")).sendKeys(testData.get("phoneNumber").asText());
            log.info("mobile number");
            driver.findElement(By.name("contactEmail")).sendKeys(testData.get("email").asText());
            log.info("email");

            By scSelector = By.name("spouseCoverage");
            WebElement  spouseCoverage = driver.findElement(scSelector);
            Select selectSC = new Select(spouseCoverage);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(scSelector));
            selectSC.selectByVisibleText(testData.get("spouseCover").asText());
            log.info("spouse Coverage");


            WebElement getquoteBtn = driver.findElement(By.cssSelector("img[src='library/images/buttons/btn-getquote.gif']"));
            actionProvider.moveToElement(getquoteBtn).build().perform();
            getquoteBtn.click();

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            log.info("========= loading page 2-1 =============");

            WebElement getQuoteBForm = driver.findElement(By.name("getQuoteBForm"));
            WebElement getQuoteBFormRowBlankBTN = getQuoteBForm.findElement(By.cssSelector("div[class='formRowBlankBTN']"));
            WebElement proceedBtn = getQuoteBFormRowBlankBTN.findElement(By.cssSelector("img[src='library/images/buttons/btn-proceed.gif']"));
            fluentWaitUtils(proceedBtn);
            executor.executeScript("arguments[0].scrollIntoView(true);", proceedBtn);
            proceedBtn.click();



            log.info("========= loading page 3 =============");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.findElement(By.name("dob")).click();
            By monthSelector = By.cssSelector("select[class='ui-datepicker-month']");
            WebElement  datepickerMonth = driver.findElement(monthSelector);
            Select selectMonth = new Select(datepickerMonth);
//            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(monthSelector));
            selectMonth.selectByVisibleText(testData.get("dobMonthCal").asText());
            log.info("datepicker-month");


            By yearSelector = By.cssSelector("select[class='ui-datepicker-year']");
            WebElement  datepickerYear = driver.findElement(yearSelector);
            Select selectYear = new Select(datepickerYear);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(yearSelector));
            selectYear.selectByVisibleText(testData.get("dobYear").asText());
            log.info("datepicker-year");



            List<WebElement> tdsDate = driver.findElements(By.cssSelector("td[data-month='3']"));
            for (WebElement e : tdsDate) {
//                log.info(e.findElement(By.tagName("a")).getText());
                if(e.findElement(By.tagName("a")).getText().equalsIgnoreCase("8")) {
                    log.info("datepicker-date");
                    e.click();
                    break;
                }
            }



            By gdSelector = By.name("gender");
            WebElement  gender = driver.findElement(gdSelector);
            Select selectGD = new Select(gender);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(gdSelector));
            selectGD.selectByVisibleText(testData.get("gender").asText());
            log.info("gender");


            By msSelector = By.name("maritalStatus");
            WebElement  maritalStatus = driver.findElement(msSelector);
            Select selectMS = new Select(maritalStatus);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(msSelector));
            selectMS.selectByVisibleText(testData.get("MaritalStatus").asText());
            log.info("MaritalStatus");

            driver.findElement(By.id("postcode")).sendKeys(testData.get("postCode").asText());

            driver.findElement(By.id("address")).sendKeys(testData.get("block").asText());
            driver.findElement(By.id("streetName")).sendKeys(testData.get("street").asText());
            driver.findElement(By.id("unitNo")).sendKeys(testData.get("unitNo").asText());
            driver.findElement(By.id("buildingName")).sendKeys(testData.get("building").asText());


            WebElement startDate = driver.findElement(By.name("startDate"));
            startDate.click();
            startDate.sendKeys(Keys.ENTER);


            By pfSelector = By.name("paymentFrequency");
            WebElement  paymentFrequency = driver.findElement(pfSelector);
            Select selectPF = new Select(paymentFrequency);
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(pfSelector));
            selectPF.selectByVisibleText(testData.get("annual").asText());
            log.info("paymentFrequency");

            WebElement completeQuoteForm = driver.findElement(By.name("completeQuoteForm"));
            WebElement formRowBlankBTN = completeQuoteForm.findElement(By.cssSelector("div[class='formRowBlankBTN']"));
            WebElement page3ProceedBtn = formRowBlankBTN.findElement(By.cssSelector("img[src='library/images/buttons/btn-proceed.gif']"));
            executor.executeScript("arguments[0].scrollIntoView(true);", page3ProceedBtn);
            page3ProceedBtn.click();


            log.info("========= loading page 4 =============");

//            Thread.sleep(25000);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            driver.findElement(By.id("tandc")).click();

            WebElement summaryForm = driver.findElement(By.name("summaryForm"));
            WebElement summaryFormRowBlankBTN = summaryForm.findElement(By.cssSelector("div[class='formRowBlankBTN']"));
            WebElement page4ProceedBtn = summaryFormRowBlankBTN.findElement(By.cssSelector("img[src='library/images/buttons/btn-proceed.gif']"));
            executor.executeScript("arguments[0].scrollIntoView(true);", page4ProceedBtn);
            page4ProceedBtn.click();

            log.info("========= loading page 5 =============");

//            Thread.sleep(25000);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.findElement(By.name("paymentMode")).click();

            WebElement page5PaynowBtn = driver.findElement(By.cssSelector("img[src='library/images/buttons/btn-paynow.gif']"));
            executor.executeScript("arguments[0].scrollIntoView(true);", page5PaynowBtn);
            page5PaynowBtn.click();

            log.info("========= loading payment page =============");

//            Thread.sleep(6000);
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            new WebDriverWait(driver, 30).until(ExpectedConditions.urlToBe(testData.get("paymentUrl").asText()));


        } catch (Exception e) {
            log.error("health check >> exception =" + e);
        }

    }


    @Then("^PA portal reach payment page$")
    public void i_am_able_to_reach_payment_page() throws Exception {

        try{
            String currentUrl = driver.getCurrentUrl();
            log.info("current url = " + currentUrl);
            Assert.assertTrue("PA portal NOT reach payment page, please CHECK again", currentUrl.equalsIgnoreCase(testData.get("paymentUrl").asText()));

            log.info("PA health check end .... PASS ");
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
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(ElementClickInterceptedException.class);
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }


}
