Feature: GI PA Health Check
  As a user I need do GI portal health check

  @pa
  Scenario: Launch PA portal do health check
    Given I prepare test data for PA portal
      """json
            {
               "nric": "S0682193F",
               "familyName": "Exxxxxx",
               "givenName": "Yeeeeeee",
               "email": "tao_liu@aviva-asia.com",
               "postCode": "190002",
               "salutation": "Mr",
               "class1":"Class 1",
               "gender": "Male",
               "MaritalStatus": "Single",
               "spouseCover": "Self only",
               "phoneNumber": "99999999",
               "dwellType": "Detached",
               "dobDate": "08",
               "dobMonth": "04",
               "dobMonthCal": "Apr",
               "dobYear": "1984",
               "block": "4",
               "unitNo": "33-99",
               "street": "street x",
               "building": "building y",
               "annual": "Annual",
               "makeModelStartWith": "b",
               "yearOfRegistration": "2015",
               "registrationNum": "SDN9999U",
               "ncd": "0",
               "licenseYears": "5 or more",
               "previousInsurer": "Aviva Ltd.",
               "page2img": "getquote.gif",
               "page3img": "completequote.gif",
               "page4img": "summary.gif",
               "page5img": "purchase.gif",
               "paymentUrl": "https://secureacceptance.cybersource.com/payment"
            }
            """
    When I navigate to PA "https://login.aviva.com.sg/directinsurance/pa.htm" and key in all data
    Then PA portal reach payment page