@smoke
Feature: Add a New Customer
  As a user
  I want to add a new customer to the system
  So that I can manage customer information effectively

  Scenario: Successfully add a new customer with valid details
    Given I am on the "Add Customer" screen
    When I enter "John Doe" in the "Name" field
    And I enter "john.doe@example.com" in the "Email" field
    And I click the "Save" button
    Then the customer "John Doe" should be added to the system
    And I should be redirected to the previous screen