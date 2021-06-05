package com.selenium.configure.environment;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Locale;

/**
 * This class select and configure the Driver according to your browser selection on the POM.
 *
 * @aurhor
 */
public class WebDriverFactory {
    static String resourceFolder = "resources/drivers/";
    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(WebDriverFactory.class);

    private static WebDriverFactory instance = null;

    private WebDriverFactory() {
    }

    /**
     * Singleton pattern
     *
     * @return a single instance
     */
    public static WebDriverFactory getInstance() {
        if (instance == null) {
            instance = new WebDriverFactory();
        }
        return instance;
    }


    public static WebDriver createNewWebDriver(String browser, String os, String headless) {
        WebDriver driver;

        /******** The driver selected is Local: Firefox  ********/
        if ("FIREFOX".equalsIgnoreCase(browser)) {
            if ("WINDOWS".equalsIgnoreCase(os)) {
                System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver.exe");
            } else {
                System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver");
            }
            driver = new FirefoxDriver();
        }

        /******** The driver selected is Chrome  ********/

        else if ("CHROME".equalsIgnoreCase(browser)) {
            if ("WINDOWS".equalsIgnoreCase(os)) {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver.exe");
            } else {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver");
            }
//	         driver = new ChromeDriver();
            ChromeOptions options = new ChromeOptions();
            if (headless != null && headless.equalsIgnoreCase("true")) {
                options.setHeadless(true);
            }
            driver = new ChromeDriver(options);

        }

        /******** The driver selected is Internet Explorer ********/
        else if ("IE".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.ie.driver", resourceFolder + os + "/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }

        /******** The driver selected is edge  ********/

        else if ("MSEDGE".equalsIgnoreCase(browser)) {
            if ("WINDOWS".equalsIgnoreCase(os)) {
                System.setProperty("webdriver.edge.driver", resourceFolder + os + "/msedgedriver.exe");
            } else {
                System.setProperty("webdriver.edge.driver", resourceFolder + os + "/msedgedriver");
            }

            driver = new EdgeDriver();
        }

        /******** The driver is not selected  ********/
        else {
            log.error("The Driver is not selected properly, invalid name: " + browser + ", " + os);
            return null;
        }

        return driver;
    }
}
