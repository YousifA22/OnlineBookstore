package com.example.sysc4806_online_bookstore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class NormalUser {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Integer id;

    private int userID;

    private String username;

    @ManyToMany
    @JoinTable(
            name = "normal_user_shopping_cart",
            joinColumns = @JoinColumn(name = "normal_user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> shoppingCart;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Set<Book> purchases;

    @ManyToMany
    @JoinTable(
            name = "normal_user_owned_books",
            joinColumns = @JoinColumn(name = "normal_user_id"),
            inverseJoinColumns = @JoinColumn(name = "owned_books_id")
    )
    private List<Book> ownedBooks;

    private String password;

    public NormalUser (String username, int userID,String password){
        this.username = username;
        this.userID = userID;
        this.shoppingCart = new ArrayList<>();
        this.password=password;
        //this.purchases= new HashSet<>();
        this.ownedBooks = new ArrayList<>();
    }

    public NormalUser() {
    }

    public void setUserID(int i){
        userID = i;
    }
    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){return password;}

    public List<Book> getShoppingCart() {
        return shoppingCart;
    }

    public void addToShoppingCart(Book b){
        shoppingCart.add(b);
    }

    public void removeFromShoppingCart(Book b){
        shoppingCart.remove(b);
    }

    public Set<Book> getPurchases() {
        HashSet purchases = new HashSet<>(ownedBooks);
        return purchases;
    }

    public HashSet<String> getPurchasedAuthors() {
        HashSet authors = new HashSet();
        for (Book book: ownedBooks) {
            authors.add(book.getAuthor());
        }
        return authors;
    }

    public HashSet<String> getPurchasedPublishers() {
        HashSet publishers = new HashSet<>(ownedBooks);
        for (Book book: ownedBooks) {
            publishers.add(book.getPublisher());
        }
        return publishers;
    }

    public double jaccardDistance(NormalUser otherUser) {
        HashSet<Book> intersection = new HashSet<>(ownedBooks);
        HashSet<Book> union = new HashSet<>(ownedBooks);
        double jaccardDistance;

        intersection.retainAll(otherUser.getPurchases());
        union.addAll(otherUser.getPurchases());
        jaccardDistance = 1.0 - (double) intersection.size() / union.size();

        return jaccardDistance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void buyBook(Book b){ownedBooks.add(b); }

    public List<Book> getOwnedBooks(){return ownedBooks; }

}
