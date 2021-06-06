Feature: GI Motor Health Check
  As a user I need do GI portal health check

  @motor
  Scenario: Launch Motor portal do health check
    Given I prepare test data for Motor portal
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
               "page2url": "https://login.aviva.com.sg/directinsurance/gimotor.htm?execution=e1s2",
               "page3url": "https://login.aviva.com.sg/directinsurance/gimotor.htm?execution=e1s3",
               "page4url": "https://login.aviva.com.sg/directinsurance/gimotor.htm?execution=e1s4",
               "page5url": "https://login.aviva.com.sg/directinsurance/gimotor.htm?execution=e1s5",
               "page6url": "https://login.aviva.com.sg/directinsurance/gimotor.htm?execution=e1s6",
               "paymentUrl": "https://secureacceptance.cybersource.com/payment"
            }
            """
    When I navigate to Motor "https://login.aviva.com.sg/directinsurance/gimotor.htm" and key in all data
    Then Motor portal reach payment page