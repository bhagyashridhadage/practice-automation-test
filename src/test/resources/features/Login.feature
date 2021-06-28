Feature: Login

  @login @MF-001
  Scenario Outline: Customer_logins_with_<scenario>
    Given I am valid PARA Bank user
    When I logins with credential from "<TC_ID>"
      | scenario   |
      | <scenario> |
    Then login is successful

    @dryRun
    Examples: 
      | TC_ID  | outcome              | scenario           |
      | TC_001 | loading account page | Valid_Credentials  |
      | TC_002 | loading account page | Incorrect_UserName |
