#@completeReviewStatus
#Feature: status of tests latest test result
#
#  Scenario: create new tests list and change status to pending
#    Given Log in as client root "root@moichor.com" user
#    And create the new tests list
#    Given Log in as admin root "root@moichor.com" user
#    When change status latest tests result to pending on admin side
#    Then latest test status should be changed on pending
#
#  Scenario: verify tests status on client side
#    Given log in as selenium root "selenium@yopmail.com" user
#    When Open the latest notification option
#    Then the latest test status  should displayed in "Pending"
#
#  Scenario: completed the test status
#    Given Log in as admin root "root@moichor.com" user
#    And change the latest  status of tests is completed
#
#  Scenario: Request for pathology
#    Given Log in as client root "root@moichor.com" user
#    When change the completed test status to Request pathology review
#    And Log in as admin root "root@moichor.com" user
#    Then admin user will see the status of tests is "Pathology Review"








