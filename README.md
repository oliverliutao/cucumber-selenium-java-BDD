# Selenium-Cucumber BDD 

## 1. Config your OS, Browser and Log Level on the POM.

On the pom.xml file you can choose between:
- Several OS: Windows, Mac, Linux.
- Several Browsers: Chrome, msedge, Firefox, IE.
- Several log level configuration:  All, Debug, Info, Warn, Error, Fatal, Off.

You just need to change the following lines:

```bash
<!-- Test Browser -->
<!-- This Parameters select where run the test 
[Remote ,Firefox ,Chrome ,msedge ,Internet Explorer] -->
<browser>YOUR_BROWSER</browser>

<!-- Test Operative System [linux, mac, windows]-->
<os>YOUR_OS</os>

<!-- Log Mode Section -->
<!-- Parameter for logger level use in this order to include the right information 
[ALL > DEBUG > INFO > WARN > ERROR > FATAL > OFF]-->
<log.level>YOUR_LOG_MODE</log.level>
```

## 2. run test:

```bash
mvn clean test
```
### test specific scenario, e.g. travel

in RunCukesTest file, @CucumberOptions config

```bash
tags = "@travel" 
```
or 
```bash
tags = "not @home"
```

### define scenarios' tag in feature file 

## 3. feature file
### to define feature and scenarios
```bash
GIHealthCheck.feature
```

## 4. Step Definitions. 
### manipulate browser to do actual health check actions defined here: 
- HomeHealthCheckSteps
- MotorHealthCheckSteps
- TravelHealthCheckStpes
- PAHealthCheckSteps


## 5. Switch browser drivers

### run mvn test command with browser name

```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=msedge
mvn clean test -Dbrowser=ie
```
or

### update pom.xml

```bash
<browser>firefox</browser>
<browser>chrome</browser>
<browser>msedge</browser>
<browser>ie</browser>
```

## 6. Download browser driver, make sure driver compatible with your browser

- chrome here: https://chromedriver.storage.googleapis.com/index.html
- firefox here: https://github.com/mozilla/geckodriver/releases
- edge here: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
- IE here: https://selenium-release.storage.googleapis.com/index.html

download browser driver and put to folder :
```bash
resources/drivers/${os}/
```

[Note]Microsoft edge compatible versions:
- browser version: 91.0.864.41 
- msedgedriver version: 90.0.818.66


## 7. Cucumber upgrade
check deprecated API
https://www.javadoc.io/doc/io.cucumber/cucumber-java/latest/index.html
https://www.javadoc.io/doc/io.cucumber/cucumber-core/6.10.2/deprecated-list.html
