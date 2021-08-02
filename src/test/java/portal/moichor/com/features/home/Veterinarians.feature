#@vet
#Feature: Veterinarians test
#
#  Background:
#    Given Log in as client root "root@moichor.com" user
#    And Go to the Veterinarians page
#
#  @regression
#  Scenario: verify the cancel and close button on Create Veterinarian popup window
#    When click on close icon button
#    Then Create Veterinarian popup window will be closed
#    When click on cancel button
#    Then Create Veterinarian popup window will be closed
#
#  @regression
#      #todo because not getting validation message for existing user
#  Scenario Outline: valid the error message for invalid field
#    When enter the firstName: "<firstName>", lastName: "<lastName>", email: "<email>"
#    Then validation message should appear for firstNameField: "<firstNameField>", lastNameFiled: "<lastNameFiled>", emailField "<emailField>"
#    Examples:
#      | firstName | lastName | email     | firstNameField                | lastNameFiled                | emailField                                                        |
#      |           |          |           | First name is required field. | Last name is required field. | Please enter client email address in format: yourname@example.com |
#      | Test      |          |           |                               | Last name is required field. | Please enter client email address in format: yourname@example.com |
#      | Test      | Test     |           |                               |                              | Please enter client email address in format: yourname@example.com |
#      | test      | Test     | Test@.com |                               |                              | Please enter client email address in format: yourname@example.com |
#  #    | 1234      | 1234     | Test@gamil.com |                               |                              | Please enter client email address in format: yourname@example.com |
##      | Test      | Test     | test@gmail.com |                               |                              |Veterinarians use is already exist                                                                  |
#
#  @smoke
#  Scenario:  add new Veterinarians and edit the added Veterinarians
#    When Add the new Veterinarians
#    And success add Veterinarian toast message is displayed
#    Then added Veterinarians is displayed
#    When edit the added Veterinarians
#    And success edit Veterinarian toast message is displayed
#    Then edit Veterinarians is displayed
#
#
#
