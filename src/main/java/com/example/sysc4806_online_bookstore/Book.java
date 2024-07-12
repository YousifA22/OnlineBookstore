package com.example.sysc4806_online_bookstore;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages="com.example.sysc4806_online_bookstore")
@Entity
public class Book {

    private String title;
    private String author;
    private String ISBN;
    private String publisher;
    private String bookDescription;
    private double price;
    private String picture;  //TO-DO
    private boolean isAvailable = true;

    private Integer stock = 0;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Integer id;

    //picture passing TO-DO later
    public Book(String title, String author, String ISBN, String publisher, String bookDescription, double price, int stock){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.bookDescription = bookDescription;
        this.price = price;
        this.stock = stock;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public double getPrice() {
        return price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor (String author){
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock){this.stock = stock;}

    public int getStock(){return this.stock;}

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
