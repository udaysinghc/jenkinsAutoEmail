@login
Feature: positive Login test

  Scenario: Login as a valid  user
    Given Log in as client root "root@moichor.com" user

#  Scenario Outline: login as an invalid user credentials
#    When enter email: "<email>" and password: "<password>"
#    Then validation message should appear for email: "<emailField>" and "<passwordField>"
#    Examples:
#      | email            | password   | emailField                      | passwordField                            |
#      |                  |            | Please enter your email address | Please enter your password.              |
#      |                  | test4566!! | Please enter your email address |                                          |
#      | test@gmail.com   |            |                                 | Please enter your password.              |
#      | test@gmail.com   | test111!!! |                                 |                                          |
#      | test@142         | test111!!! | Invalid email address           |                                          |
#      | test@gmail.com   | 12345      |                                 | Password must be 6 characters or longer. |
#      | root@moichor.com | test456!!! |                                 |                                          |

#  Scenario: login selenium
#    Given log in as selenium root "selenium@yopmail.com" user


