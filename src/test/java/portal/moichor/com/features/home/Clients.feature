@clients
Feature: Client page test

  Background:
    Given Log in as client root "root@moichor.com" user
    And Go to the Clients page

#  Scenario Outline: valid the error message for invalid field
#    When enter the client firstName: "<firstName>", lastName: "<lastName>"
#    Then validation message should appear for firstNameField: "<firstNameField>", lastNameFiled: "<lastNameFiled>"
#    Examples:
#      | firstName | lastName | firstNameField                | lastNameFiled                |
#      |           |          | First name is required field. | Last name is required field. |
#      | test      |          |                               | Last name is required field. |
#      |           | test     | First name is required field. |                              |
#
#  Scenario Outline: validate the error message for long characters
#    When enter the long characters in firstName: "<firstName>",lastName: "<lastName>"
#    Then validation message will be displayed for long character "<character>"
#    Examples:
#      | firstName                           | lastName                            | character                                     |
#      | Hi am Uday, automate the test cases | Hi am Uday, automate the test cases | value too long for type character varying(20) |
#      | Test                                | Hi am Uday, automate the test cases | value too long for type character varying(20) |
#      | Hi am Uday, automate the test cases | Test                                | value too long for type character varying(20) |

  Scenario:  add new Clients
    When create client firstName "Client" lastname and email "moicher@yopmail.com"
    And successfully added Clients toast message is displayed
    Then added Clients is displayed

#
#  Scenario Outline: search filter client
#    When  search for "<clientName>"
#    And enter name on search field "<searchName>"
#    Then search client name "<searchClientName>"  will displayed in sequence
#    Examples:
#      | clientName | searchName | searchClientName |
#      | firstName  | uday       | uday singh       |
#      | lastName   | singh      | uday singh       |
#
#  Scenario: edit the added Clients
#    When edit the added Clients
#    And success edit Clients toast message is displayed
#    Then edited Clients is displayed
#
#  Scenario: verify the patient tab on clients list
#    When open the client name
#    Then patients tab is displayed
#
#  Scenario: Delete the added clients
#    When delete the added Clients
#    Then success  Clients is deleted toast message is displayed
#
