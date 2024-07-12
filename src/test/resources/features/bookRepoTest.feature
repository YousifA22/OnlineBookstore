Feature: Book Repository Management

  Scenario: Saving and retrieving a book in the book repository
    Given a book with title "1984", author "George Orwell", ISBN "123456789", publisher "Secker and Warburg", description "Dystopian novel", price 8.99, and stock 5
    When the book is saved in the repository
    Then the book should be retrievable with the same details