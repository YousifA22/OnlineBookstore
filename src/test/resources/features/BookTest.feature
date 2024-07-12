Feature: Book Creation

    Scenario: Creating a book and testing functionality

     Given Book with title "Hatchet", author "Hasan", ISBN "51268715", publisher "Christopher", description "Adventurous novel about survival", price 13.99, and stock 10
     When the book is successfully created
     Then the book has been created with the same parameters passed by the user
