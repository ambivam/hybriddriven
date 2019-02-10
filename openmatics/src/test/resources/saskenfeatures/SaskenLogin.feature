Feature: Logging into Sasken Portal

  @SaskenSmoke @All
  Scenario: Logging Into sasken Application Portal
    Given User launches the Application
    And  Logins into the Application by providing credentials
    Then Sasken Home page is displayed and "sasken.com" should be validated
   
  @SaskenRegression @All
  Scenario: Logging Into sasken Application Portal and validating invalid text
    Given User launches the Application
    And  Logins into the Application by providing credentials
    Then Sasken Home page is displayed and "sasken2.com" should be validated