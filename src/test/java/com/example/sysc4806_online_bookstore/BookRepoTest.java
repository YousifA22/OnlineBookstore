package com.example.sysc4806_online_bookstore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookRepoTest {

    @Autowired
    private BookRepo bookRepository;
    private Book savedBook;

    @Given("a book with title {string}, author {string}, ISBN {string}, publisher {string}, description {string}, price {double}, and stock {int}")
    public void a_book_with_details(String title, String author, String isbn, String publisher, String description, double price,int stock) {
        Book book = new Book(title, author, isbn, publisher, description, price,stock);
        savedBook = bookRepository.save(book);
    }

    @When("the book is saved in the repository")
    public void the_book_is_saved_in_the_repository() {
        assertNotNull(savedBook);
    }

    @Then("the book should be retrievable with the same details")
    public void the_book_should_be_retrievable_with_the_same_details() {
        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);
        assertNotNull(foundBook);
        assertEquals(savedBook.getTitle(), foundBook.getTitle());
        assertEquals(savedBook.getAuthor(), foundBook.getAuthor());
    }
}
