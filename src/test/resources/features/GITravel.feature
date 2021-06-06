Feature: GI Travel Health Check
  As a user I need do GI portal health check

  @travel
  Scenario: Launch Travel portal do health check
    Given I prepare test data for Travel portal
      """json
            {
               "nric": "S0682193F",
               "familyName": "Exxxxxx",
               "givenName": "Yeeeeeee",
               "email": "tao_liu@aviva-asia.com",
               "postCode": "190002",
               "salutation": "Mr",
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
               "page2url": "https://login.aviva.com.sg/gi/travel/#/addOns",
               "page3url": "https://login.aviva.com.sg/gi/travel/#/addRider",
               "page4url": "https://login.aviva.com.sg/gi/travel/#/completeQuote",
               "page5url": "https://login.aviva.com.sg/gi/travel/#/completeQuoteSummary",
               "page6url": "https://login.aviva.com.sg/gi/travel/#/purchase",
               "paymentUrl": "https://secureacceptance.cybersource.com/payment"
            }
            """
    When I navigate to Travel "https://login.aviva.com.sg/gi" and key in all data
    Then Travel portal reach payment page