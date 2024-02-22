Feature: Manage orders in PetStore
  Scenario: Create a new order in the PetStore
    Given PetStore manager wants to organize the inventory
    When the manager creates a new order with valid data
    Then the manager validates that order was created in the inventory
    And the manager validates quantities in the inventory
@Store
  Scenario: Delete an order in the PetStore
    Given PetStore manager wants to organize the inventory
    When the manager deletes an order with id 5
    Then the manager validates the order 5 was eliminated in the inventory