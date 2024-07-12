Feature: BookStore EditBook feature

  Scenario Outline: Get the BookStore Inventory
    Given A Book is added to the BookStore
    When The EditBook EditURL is used <EditURL>
    Then Check if the connection with the Inventory (main) page is ok and updated inventory
    Examples:
      | EditURL  |
      | "/getBook?ID=2" |