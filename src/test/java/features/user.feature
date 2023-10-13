Feature: User API Testing

  Scenario Outline: Create users with given input array
    Given user details <id> <userName> <firstName> <lastName> <email> <password> <phone> <userStatus>
    When user invokes the post API to create users
    Then All the users are created.
    Examples:
      | id  | userName  | firstName | lastName | email                 | password   | phone        | userStatus |
      | 100 | "user100" | "test1"   | "rest"   | "test1.rest@demo.com" | "Test1234" | "1234567890" | 1          |

  Scenario Outline: Create users with given input list
    Given user details <id> <userName> <firstName> <lastName> <email> <password> <phone> <userStatus>
    When user invokes the post API to create users with list
    Then All the users are created.
    Examples:
      | id  | userName  | firstName | lastName | email                 | password   | phone        | userStatus |
      | 100 | "user100" | "test1"   | "rest"   | "test1.rest@demo.com" | "Test1234" | "1234567890" | 1          |

  Scenario Outline: Get user details by user name
    Given user id <username>
    When user invokes the get api to fetch user details
    Then user <username> specific details are retrieved
    Examples:
      | username  |
      | "user100" |

  Scenario Outline: Update user with given input list
    Given user details <id> <userName> <firstName> <lastName> <email> <password> <phone> <userStatus>
    When user invokes the PUT API to create users with list
    Then user is updated with latest data.
    Examples:
      | id  | userName  | firstName | lastName | email                 | password   | phone        | userStatus |
      | 100 | "user100" | "test2"   | "rest"   | "test1.rest@demo.com" | "Test1234" | "1234567890" | 1          |

  Scenario Outline: Delete user by user name
    Given user id <username>
    When user invokes the delete api to delete user
    Then user <username> is deleted
    Examples:
      | username  |
      | "user100" |