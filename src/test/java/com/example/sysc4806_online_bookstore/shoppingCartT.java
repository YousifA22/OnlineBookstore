package com.example.sysc4806_online_bookstore;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class shoppingCartT {

    private NormalUser user;

    private Book b;


    @Given("A Book {string} and user {string} are created")
    public void aBookTitleAndUserUsernameAreCreated(String title, String username) {
        user = new NormalUser(username,0,"password");
        b = new Book(title,null,null,null,null,0,0);
    }

    @When("A book is added to the user cart")
    public void aBookIsAddedToTheUserCart() {
        user.addToShoppingCart(b);
    }

    @Then("verify that the book is added")
    public void verifyThatTheBookIsAdded() {
        if(!user.getShoppingCart().contains(b)){
            throw new RuntimeException("didnt add book");
        }
    }
    @When("The book is removed from the cart")
    public void theBookIsRemovedFromTheCart() {
        user.removeFromShoppingCart(b);
    }

    @Then("verify that the book is removed")
    public void verifyThatTheBookIsRemoved() {
        if(user.getShoppingCart().contains(b)){
            throw new RuntimeException("didnt remove book");
        }
    }
}