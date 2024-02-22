Feature: Manage users in PetStore

  Scenario: Create a user
    Given PetStore manager wants to organize the users
    When the manager creates a new user with valid data
    Then the manager validates the new user was created

  Scenario: Realize user login and logout
    Given PetStore manager wants to organize the users
    When the manager logs in with user "user1" and password "XXXXXXXXXXX"
    Then the manager logs the user out

  Scenario: Update a user
    Given PetStore manager wants to organize the users
    When the manager updates the user "user1" with valid data
    Then the manager validates user was updated

  Scenario: Delete a user
    Given PetStore manager wants to organize the users
    When the manager deletes the user "user7"
    Then the manager validates "user7" was deleted








