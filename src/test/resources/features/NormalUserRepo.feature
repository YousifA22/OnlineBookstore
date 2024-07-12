Feature: User Repository Management

  Scenario: Saving and retrieving a user in the user repository
    Given a user with name "chris" and userID 12 and password "Hello"
    When the user is saved in the repository
    Then the user should be retrievable with the same details