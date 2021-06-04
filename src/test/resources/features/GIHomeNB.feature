Feature: Home Health Check
        As a support I need do home portal health check
@smoke
  Scenario: Launch Home portal do health check
    Given I navigate to "https://login.aviva.com.sg/hm"
    When I fill in all data
    Then I am able to reach payment page
