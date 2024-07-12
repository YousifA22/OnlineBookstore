Feature: BookStore getInventory feature

  Scenario Outline: Get the BookStore Inventory
    Given The Inventory URL <inventoryURL>
    When The URL is used to get the Inventory (main) page
    Then Check if the connection with the Inventory (main) page is ok
    Examples:
      | inventoryURL  |
      | "/" |