package com.example.sysc4806_online_bookstore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookTest {

    private Book book;
    private String title;
    private String author;
    private String ISBN;
    private String publisher;
    private String bookDescription;
    private double price;

    private int stock;

    @Given("Book with title {string}, author {string}, ISBN {string}, publisher {string}, description {string}, price {double}, and stock {int}")
    public void a_book_created(String title, String author, String isbn, String publisher, String description, double price, int stock) {
        this.title = title;
        this.author = author;
        this.ISBN = isbn;
        this.publisher = publisher;
        this.bookDescription = description;
        this.price = price;
        this.stock = stock;
    }

    @When("the book is successfully created")
    public void create_a_book(){
        book = new Book(title, author, ISBN, publisher, bookDescription, price,stock);
    }

    @Then("the book has been created with the same parameters passed by the user")
    public void check_all_book_properties(){
        Assertions.assertEquals(book.getAuthor(), author);
        Assertions.assertEquals(book.getTitle(), title);
        Assertions.assertEquals(book.getBookDescription(), bookDescription);
        Assertions.assertEquals(book.getISBN(), ISBN);
        Assertions.assertEquals(book.getPublisher(), publisher);
        Assertions.assertEquals(book.getPrice(), price);
    }

}