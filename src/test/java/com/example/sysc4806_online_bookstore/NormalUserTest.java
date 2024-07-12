package com.example.sysc4806_online_bookstore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NormalUserTest {

    Book b;
    NormalUser user;

    @Before
    public void setUp() throws Exception {
        b = new Book("Hatchet", "John", "1234-5-6789-2", "Jason", "Sharp!", 12.99,1);
        user = new NormalUser("Jake", 1011,"hello");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserID() {
        assertTrue(user.getUserID() == 1011);
    }

    @Test
    public void getUsername() {
        assertEquals(user.getUsername(), "Jake");
    }

    @Test
    public void getShoppingCart() {
        assertTrue(user.getShoppingCart() != null);
    }

    @Test
    public void addToShoppingCart() {
        user.addToShoppingCart(b);
        assertEquals(user.getShoppingCart().get(0), b);
    }

    @Test
    public void removeFromShoppingCart() {
        user.removeFromShoppingCart(b);
        assertTrue(user.getShoppingCart().isEmpty());
    }
}