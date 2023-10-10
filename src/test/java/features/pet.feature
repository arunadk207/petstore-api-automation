Feature: PetStore application
  Scenario: Create a Pet
    Given pet id "1" and name as "tommy"
    When user invokes the API to create a pet
    Then Pet object is created

  Scenario Outline: Update a pet
    Given pet id <petId> and name as <name>
    When user invokes the API to update a pet
    Then Pet is updated with new name
    Examples:
      | petId | name     |
      | "1"   | "Johnny" |

  Scenario: Find Pets By Status
    Given pet "available" status
    When user invokes the api to read pets
    Then all pets should be returned by status

