Feature: Training Login

  # Envirionment to pass from Global Properties file and will be handled in logic.
  @login @MF-001
  Scenario Outline: Modulr_<customerType>_logins_with_<scenario>
    Given "customerType" is valid Modulr user
    When user logins with credential from "<TC_ID>"
      | scenario   |
      | <scenario> |
    Then login is "<loginStatus>" with "<outcome>"

    @dryRun
    Examples: 
      | TC_ID  | customerType | scenario          | result     | outcome              |
      | TC_001 | SandBox      | Valid_Credentials | successful | loading account page |

    @smoke @sanity
    Examples: 
      | TC_ID  | customerType | scenario                    | result     | outcome              |
      | TC_001 | SandBox      | Valid_Credentials           | successful | loading account page |
      | TC_002 | SandBox      | Incorrect_UserName          | failed     | error message        |
      | TC_003 | SandBox      | Incorrect_Password          | failed     | error message        |
      | TC_004 | SandBox      | Blank_UserName_And_Password | failed     | error message        |
      | TC_005 | SandBox      | Blank_UserName              | failed     | error message        |
      | TC_006 | SandBox      | Blank_Password              | failed     | error message        |
      | TC_007 | SandBox      | Double_Login                | failed     | error message        |

    @regression
    Examples: 
      | TC_ID  | customerType     | scenario                    | result     | outcome              |
      | TC_008 | Developer        | Valid_Credentials           | successful | loading account page |
      | TC_009 | Developer        | Incorrect_UserName          | failed     | error message        |
      | TC_010 | Developer        | Incorrect_Password          | failed     | error message        |
      | TC_011 | Developer        | Blank_UserName_And_Password | failed     | error message        |
      | TC_012 | Developer        | Blank_UserName              | failed     | error message        |
      | TC_013 | Developer        | Blank_Password              | failed     | error message        |
      | TC_014 | Developer        | Double_Login                | failed     | error message        |
      | TC_015 | Direct Customers | Valid_Credentials           | successful | loading account page |
      | TC_016 | Direct Customers | Incorrect_UserName          | failed     | error message        |
      | TC_017 | Direct Customers | Incorrect_Password          | failed     | error message        |
      | TC_018 | Direct Customers | Blank_UserName_And_Password | failed     | error message        |
      | TC_019 | Direct Customers | Blank_UserName              | failed     | error message        |
      | TC_020 | Direct Customers | Blank_Password              | failed     | error message        |
      | TC_021 | Direct Customers | Double_Login                | failed     | error message        |
      | TC_022 | Partners         | Valid_Credentials           | successful | loading account page |
      | TC_023 | Partners         | Incorrect_UserName          | failed     | error message        |
      | TC_024 | Partners         | Incorrect_Password          | failed     | error message        |
      | TC_025 | Partners         | Blank_UserName_And_Password | failed     | error message        |
      | TC_026 | Partners         | Blank_UserName              | failed     | error message        |
      | TC_027 | Partners         | Blank_Password              | failed     | error message        |
      | TC_028 | Partners         | Double_Login                | failed     | error message        |
      | TC_029 | Delegates        | Valid_Credentials           | successful | loading account page |
      | TC_030 | Delegates        | Incorrect_UserName          | failed     | error message        |
      | TC_031 | Delegates        | Incorrect_Password          | failed     | error message        |
      | TC_032 | Delegates        | Blank_UserName_And_Password | failed     | error message        |
      | TC_033 | Delegates        | Blank_UserName              | failed     | error message        |
      | TC_034 | Delegates        | Blank_Password              | failed     | error message        |
      | TC_035 | Delegates        | Double_Login                | failed     | error message        |

  @login @MF-001
  Scenario Outline: Modulr_<customerType>_recieves_warning_for_account_to_get_locked_after_<noOfTry>_worng_password_attempts
    Given "customerType" is valid Modulr user
    When user logins with credential from "<TC_ID>"
      | scenario   | noOfAttempts |
      | <scenario> |            5 |
    Then login is "<loginStatus>" with "<outcome>"

    @smoke @sanity
    Examples: 
      | TC_ID  | customerType | scenario       | loginStatus | outcome        |
      | TC_036 | SandBox      | Wrong_Attempts | Paused      | warningMessage |

    @regression
    Examples: 
      | TC_ID  | customerType     | scenario       | loginStatus | outcome        |
      | TC_037 | Developer        | Wrong_Attempts | Paused      | warningMessage |
      | TC_038 | Direct Customers | Wrong_Attempts | Paused      | warningMessage |
      | TC_039 | Partners         | Wrong_Attempts | Paused      | warningMessage |
      | TC_040 | Delegates        | Wrong_Attempts | Paused      | warningMessage |

  @login @MF-001
  Scenario Outline: Modulr_<customerType>_should_able_to_reset_password
    Given "customerType" is valid Modulr user
    When user want to reset password
    Then user should have provision to reset password
    When user opt for reset pasword
    Then reset password email should get trigger to users registered emailID

    @smoke @sanity
    Examples: 
      | TC_ID  | customerType | scenario       | result |
      | TC_041 | SandBox      | Reset_Password | Paused |

    @regression
    Examples: 
      | TC_ID  | customerType     | scenario       | result |
      | TC_042 | Developer        | Reset_Password | Paused |
      | TC_043 | Direct Customers | Reset_Password | Paused |
      | TC_044 | Partners         | Reset_Password | Paused |
      | TC_045 | Delegates        | Reset_Password | Paused |
