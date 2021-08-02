#@workflowInReviewAndAddImage
#Feature: work flow
#
#  Scenario: create new test and verify the test is created successfully
#    Given Log in as client root "root@moichor.com" user
#    And create the new tests list
#
#  Scenario: verify the client details on admin side and Send to review by user
#    Given Log in as admin root "root@moichor.com" user
#    And navigate to the orders page
#    When Change the status of Tests
#    And  Also reflected in client side user
#    Given log in as selenium root "selenium@yopmail.com" user
#    When Open the latest notification tests
#    Then the test status should displayed in "In review"
#    When Write the response newly added test sample and send it back  to admin
#
#  Scenario: Add results and finalize the results and complete the test sample test.
#    Given Log in as admin root "root@moichor.com" user
#    And navigate to the orders page
#    And Open the latest test order
#    And click on view result button
#    When add images and additional fields like PCV %  eGFR%  , Albumin, Anisocytosis ,AST
#    And tests status of order is review completed
#
#  Scenario: Re-Analyze and manual review the test sample
#    Given Log in as admin root "root@moichor.com" user
#    And navigate to the orders page
#    And Open the latest test order
#    When re analyze the latest test sample result
#    Then successfully  Re-analyze toast message is displayed
#    When manual review the latest tests result
#    Then added image  attached and PCV and eGFR  values is displayed
#
#




