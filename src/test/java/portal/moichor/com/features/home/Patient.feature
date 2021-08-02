#@Patient
#Feature: patient test
#
#  Background:
#    Given Log in as client root "root@moichor.com" user
#    And Go to the patient page
#
#  Scenario Outline: valid the error message for invalid field
#    When enter patient name: "<name>", client: "<client>", species: "<species>"
#    Then validation error message should appear for name: "<nameField>", client: "<clientField>", species "<speciesField>"
#    Examples:
#      | name | client | species | nameField               | clientField               | speciesField               |
#      |      |        |         | Name is required field. | Client is required field. | Species is required field. |
#      | Test |        |         |                         | Client is required field. | Species is required field. |
#      | Test | Test   |         |                         |                           | Species is required field. |
#      | Test |        | White   |                         | Client is required field. |                            |
#
#  Scenario Outline: validation error message for Long character text
#    When enter patient name: "<patientName>"
#    Then validation error message should appear for patient name filed: "<patientNameFiled>"
#    Examples:
#      | patientName                                                                | patientNameFiled                              |
#      | As an user, I want to see the progress bar when  searching a person entity | value too long for type character varying(50) |
#
#
#  Scenario Outline:  add new patient
#    And Go to the Clients page
#    Given create client firstName "Client" lastname and email "moicher@yopmail.com"
#    And successfully added Clients toast message is displayed
#    When add patient name: "<name>", dateOfBirth: "<dateOfBirth>", sex: "<sex>", weight: "<weight>", species: "<species>"
#    And successfully added Patient toast message is displayed
#    Then added Patient is displayed
#    Examples:
#      | name    | dateOfBirth | sex  | weight | species       |
#      | Patient | 10-12-2010  | Male | 70     | White-fronted |
#
#  Scenario: Delete the Patient
#    When delete the added Patient
#    Then successfully Patient is deleted toast message is displayed
