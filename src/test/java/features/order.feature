Feature: Pet order API testing

  Scenario Outline: Place an order for a pet
    Given Pet order <id> <petId> <quantity> details
    When user invokes the order api to place an order
    Then order id <id> is placed
    Examples:
      | id  | petId | quantity |
      | 123 | 2     | 1        |

  Scenario Outline: Get order by order id
    Given order id <id>
    When user invokes the order api to get orders
    Then order id <id> specific details are retrieved
    Examples:
      | id  |
      | 123 |

  Scenario Outline: Delete order by order id
    Given order id <id>
    When user invokes the order api to delete order
    Then given order id <id> been deleted
    Examples:
      | id  |
      | 123 |

  Scenario: Fetch store inventory
    When user invokes the inventory api to get inventory details
    Then inventory details been retrieved

