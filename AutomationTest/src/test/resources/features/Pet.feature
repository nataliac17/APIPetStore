Feature: Manage Pets in PetStore
  Scenario: Create a new pet in the PetStore
    Given PetStore manager wants to organize the pets in the inventory
    When the manager creates a new pet with valid data
    Then the manager validates that "Russian Blue" was created by Tags

  Scenario: Update a pet in the PetStore
    Given PetStore manager wants to organize the pets in the inventory
    When the manager updates a pet with valid data
    Then the manager validates that "Lion 1" was updated by status

  Scenario: Delete a pet in the PetStore
    Given PetStore manager wants to organize the pets in the inventory
    When the manager deletes a pet 6
    Then the manager validates that pet 6 was deleted by ID
