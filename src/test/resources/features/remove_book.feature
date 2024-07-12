Feature: Remove Book

  Scenario: Remove a book from the inventory
    Given there is a book in the inventory
    When the user selects the delete button
    Then the book should be removed from the inventory