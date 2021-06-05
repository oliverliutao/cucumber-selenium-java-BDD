package com.test.step.defintions.healthcheck;

import com.selenium.configure.environment.PropertiesHandler;
import com.test.step.defintions.ClickSteps;
import com.test.step.defintions.Hooks;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * This class contains methods to allow you to click on an element
 * More steps examples here: https://github.com/selenium-cucumber/selenium-cucumber-java/blob/master/doc/canned_steps.md
 * @author estefafdez
 */

public class HomeHealthCheckSteps {
    WebDriver driver;

    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(ClickSteps.class);

    public HomeHealthCheckSteps(){
        driver= Hooks.getDriver();
    }

    @Given("^I navigate to \"([^\"]*)\" and fill in all data$")
    public void i_navigate_to_and_fill_in_all_data(String url) throws Exception {
        log.info("Home health check start ....");

        try {
            driver.navigate().to(url);

            By occupancyBtn = PropertiesHandler.getCompleteElement("xpath", "//body/div/div[1]/div/form/div/div[6]/div/ul/li[2]/label");
            driver.findElement(occupancyBtn).click();

            By page1DewllingType = PropertiesHandler.getCompleteElement("id", "dwellingTypeRef");
            Select opt = new Select(driver.findElement(page1DewllingType));

            if (opt.equals("text")) {
                log.info("select option: " + opt + "by text");
                opt.selectByVisibleText("Detached");
            }

        }catch(Exception e) {
            log.error("exception =" + e);
        }finally {
            driver.quit();
        }

    }


    @Then("^I am able to reach payment page$")
    public void i_am_able_to_reach_payment_page()  throws Exception
    {

        log.info("Home health end ....");
        driver.quit();
    }

}
