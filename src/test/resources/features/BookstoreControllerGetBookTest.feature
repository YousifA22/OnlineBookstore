Feature: BookStore getBook Feature

  Scenario Outline: Get a book from the Inventory
    Given A bookURL <bookURL> for getBook
    When The URL is used to get the Inventory (main) page And a Book is in Inventory
    Then Check if the connection and Book is found
    Examples:
      |bookURL|
      |"/getBook?ID=1"|