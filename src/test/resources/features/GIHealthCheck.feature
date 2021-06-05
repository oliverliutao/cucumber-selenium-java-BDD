Feature: GI Health Check
  As a user I need do GI portal health check

  @smoke
  Scenario: Launch Home portal do health check
    Given I prepare test data
        """json
      {
         "nric": "S0682193F",
         "familyName": "Exxxxxx",
         "givenName": "Yeeeeeee",
         "email": "tao_liu@aviva-asia.com",
         "postCode": "190002",
         "phoneNumber": "99999999",
         "dwellType": "Detached",
         "dobDate": "08",
         "dobMonth": "04",
         "dobYear": "1984",
         "block": "4",
         "street": "street x",
         "building": "building y",
         "makeModelStartWith": "b",
         "yearOfRegistration": "2015",
         "registrationNum": "SDN9999U",
         "ncd": "0",
         "licenseYears": "5 or more",
         "previousInsurer": "Aviva Ltd.",
         "page2url": "https://login.aviva.com.sg/hm/home/#/addOns",
         "page3url": "https://login.aviva.com.sg/hm/home/#/addRider",
         "page4url": "https://login.aviva.com.sg/hm/home/#/completeQuote",
         "page5url": "https://login.aviva.com.sg/hm/home/#/completeQuoteSummary",
         "page6url": "https://login.aviva.com.sg/hm/home/#/purchase",
         "paymentUrl": "https://secureacceptance.cybersource.com/payment"
      }
      """
    When I navigate to "https://login.aviva.com.sg/hm" and fill in all data
    Then I am able to reach payment page
