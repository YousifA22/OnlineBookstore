Feature: BookStore ShoppingCart feature

  Scenario Outline: Shopping Cart adding book, removing book
    Given A Book <title> and user <username> are created
    When A book is added to the user cart
    Then verify that the book is added
    When The book is removed from the cart
    Then verify that the book is removed
    Examples:
      | title | username |
      | "hello" | "chris" |