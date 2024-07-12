Feature: Normal User Controller

  Scenario : Creating a new user
    Given a user with username "Yousif" and userID 22 and password "Hello"
    When create the user
    Then the user creation should be successful

  Scenario: getting the shopping cart
    Given a user and a book are made
    When I get the shopping cart for the user
    Then the shopping cart has 0 item

  Scenario: Searching for a book
    Given a book in the repo
    When A search is executed
    Then the user obtains the target books(s)

  Scenario: Filtering a book by author
    Given a book in the repository
    When the user selects the "filter by author" criteria
    Then the user obtains a list of books filtered by authors

  Scenario: Filtering a book by price
    Given books are added to the repository
    When the user selects the "filter by price" criteria
    Then the user obtains a list of books filtered by price

