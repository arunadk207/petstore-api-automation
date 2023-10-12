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

  Scenario Outline: Find Pet By pet id
    Given pet pet <id>  number
    When user invokes the api to read pet
    Then pet id <id> details need to be retrieved
    Examples:
      | id |
      | 1  |
      | 200000  |

  Scenario Outline: Update pet
    Given pet <id> <name> and <status>
    When uses invokes the api to update pet
    Then pet with <id> updated
    Examples:
      | id | name | status    |
      | 1  | "test" | "available" |

  Scenario Outline: Delete pet by id
    Given pet <id> number
    When user invokes the api to delete pet
    Then pet with <id> deleted
    Examples:
      | id |
      | 1  |

