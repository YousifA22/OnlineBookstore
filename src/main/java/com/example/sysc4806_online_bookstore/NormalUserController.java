package com.example.sysc4806_online_bookstore;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class NormalUserController {

    @Autowired
    private NormalUserRepo normalUserRepo;

    @Autowired
    private BookRepo bookRepo;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam int userID, @RequestParam String password, Model m) {
        NormalUser newUser = new NormalUser(username, userID, password);
        normalUserRepo.save(newUser);
        m.addAttribute(normalUserRepo);
        m.addAttribute("NormalUser", new NormalUser());
        return "startingPage";
    }

    @PostMapping("/registeruser")
    public String registerUserwithUser(@ModelAttribute NormalUser user, Model m) {
        normalUserRepo.save(user);
        m.addAttribute(normalUserRepo);
        m.addAttribute("confirmationMessage", "User successfully registered.");
        return "startingPage";
    }

    @GetMapping("/register")
    public String register(Model m) {
        m.addAttribute("NormalUser", new NormalUser());
        return "register";
    }

    @GetMapping("/login")
    public String loginpage(Model m) {
        return "startingPage";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password,Model m) {
        NormalUser user = normalUserRepo.findByUsername(username);
        if (user == null || !user.getPassword().equals(password) ) {

            return "startingPage";
        }
        m.addAttribute(bookRepo);
        m.addAttribute(user);
        if(user.getUserID() == 4806) {
            return "redirect:/admin";
        }
        return "showInventoryUser";
    }


    @GetMapping("/addtocart")
    public String addToShoppingCart(@RequestParam Integer userId, @RequestParam Integer bookId, Model m) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("book not found"));

        if(!user.getShoppingCart().contains(book)) {
            user.addToShoppingCart(book);
        }
        normalUserRepo.save(user);
        bookRepo.save(book);
        m.addAttribute(user);
        m.addAttribute(bookRepo);

        return "showCart";
    }


    @GetMapping("/removefromcart")
    public String removeBookFromCart(@RequestParam Integer userId, @RequestParam Integer bookId, Model m) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("book not found"));
        user.removeFromShoppingCart(book);
        normalUserRepo.save(user);
        m.addAttribute(user);
        m.addAttribute(bookRepo);
        return "showInventoryUser";
    }


    @GetMapping("/cart")
    public String getShoppingCart(@RequestParam Integer userId,Model m) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        m.addAttribute(user.getShoppingCart());
        m.addAttribute("normalUser", user);
        return "shoppingCart";
    }

    @GetMapping("/ownedBooks")
    public String getOwnedBooks(@RequestParam Integer userId, Model m) {
        NormalUser user = normalUserRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        m.addAttribute(user.getOwnedBooks());
        m.addAttribute("normalUser", user);
        return "ownedBooks";
    }

    @GetMapping("/buybook")
    public String buybook(@RequestParam Integer userId, @RequestParam Integer bookId, Model m) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("book not found"));

        // Check if the book is available for purchase
        if (!book.isAvailable()) {
            m.addAttribute("error", "This book is no longer available for purchase.");
            return "showInventoryUser";
        }

        m.addAttribute("normalUser", user);
        m.addAttribute("book", book);
        return "Checkout";
    }


    @PostMapping("/Checkout")
    public String checkout(@RequestParam Integer userId, @RequestParam Integer bookId, Model m) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("book not found"));

        // Remove book from shopping, add to owned books, and to purchase history
        user.removeFromShoppingCart(book);
        user.buyBook(book);
        //user.addPurchasedItem(book);

        // Update the book stock and availability
        book.setStock(book.getStock() - 1);
        if (book.getStock() == 0) {
            book.setAvailable(false);
        }

        // Save the updated information to the database
        bookRepo.save(book);
        normalUserRepo.save(user);

        m.addAttribute("normalUser", user);
        m.addAttribute("book", book);

        return "ownedBooks";
    }



    @PostMapping("/Search")
    public String showSearchedBooks(@ModelAttribute Book b, Model model){
        List<Book> bookList = searchBooks(b, null);
        if(bookList == null){
            model.addAttribute("Book", new Book());
            return "SearchByName";
        }
        model.addAttribute(searchBooks(b, null));
        return "SearchByName2";
    }

    public List<Book> searchBooks(Book book, ArrayList<Book> testing) {
        List<Book> books = new ArrayList<>();
        ArrayList<Book> bookHolder = new ArrayList<>();
        if(testing==null) {
            books = bookRepo.findAll();
        }
        else{
            books = testing;
        }
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(book.getTitle().toLowerCase())) {
                bookHolder.add(b);
            }
            }
        if(bookHolder.isEmpty()){
            return null;
        }
        return bookHolder;
    }

    @GetMapping("/Search")
    public String getSearchedBooks(Model model){
        model.addAttribute("Book", new Book());
        return "SearchByName";
    }

    public List<Book> filterPrice(List<Book> books) {

        if(books == null){
            return books;
        }

        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = 0; j < books.size() - 1; j++) {
                if (books.get(j + 1).getPrice() > books.get(j).getPrice()) {
                    Book tempBook = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, tempBook);
                }
            }
        }
        return books;
    }
    public List<Book> filterAuthor(List<Book> books) {

        if(books == null){
            return books;
        }

        for (int i = 0; i < books.size(); i++) {
            for (int j = 0; j < books.size() - 1; j++) {
                if (books.get(j + 1).getAuthor().charAt(0) < books.get(j).getAuthor().charAt(0)) {
                    Book tempBook = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, tempBook);
                }
            }
        }

        return books;
    }

    public List<Book> covertRepoToArrayList(){ //fully functional without this method. used for testing
        List<Book> books = new ArrayList<>();
        books = bookRepo.findAll();
        return books;
    }


    @GetMapping("/Filter")
    public String filterBooks(Model m, @RequestParam String filter){
        if("author".equals(filter)){
            List<Book> listOfAuthors = filterAuthor(covertRepoToArrayList());
            m.addAttribute("filteredBooks", listOfAuthors);
        }
        else if("price".equals(filter)){
            List<Book> listOfPrices = filterPrice(covertRepoToArrayList());
            m.addAttribute("filteredBooks", listOfPrices);
        }
        return "FilteredBooks";
    }

    @GetMapping("/filterbooks")
    public String filterBook(Model model){
        model.addAttribute("books", bookRepo);
        return "DropDownFilter";
    }

    @GetMapping("/recommendations")
    public String recommendBooks(@RequestParam Integer userId, Model model) {
        NormalUser user = normalUserRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        List<NormalUser> users = normalUserRepo.findAll();
        List<Book> inventory = bookRepo.findAll();
        List<Book> pastPurchaseRecommendations = pastPurchasesRecommendations(user, inventory);
        HashSet<Book> otherUserRecommendations = otherUserRecommendations(user, users);
        model.addAttribute("user", user);
        model.addAttribute("pastPurchaseRecommendations", pastPurchaseRecommendations);
        model.addAttribute("otherUserRecommendations", otherUserRecommendations);

        return "showRecommendations";
    }

    public List<Book> pastPurchasesRecommendations(NormalUser user, List<Book> inventory) {
        ArrayList recommendations = new ArrayList<Book>();
        HashSet authors = user.getPurchasedAuthors();
        HashSet publishers = user.getPurchasedPublishers();

        for (Object author: authors) { //Add Books from inventory with the same author && isn't already purchased
            for (Book book: inventory) {
                if (author == book.getAuthor() && !user.getPurchases().contains(book)) {
                    recommendations.add(book);
                }
            }
        }
        for (Object publisher: publishers) {//Add Books with same publisher && isn't already purchased
            for (Book book: inventory) {
                if (publisher == book.getPublisher() && !user.getPurchases().contains(book)) {
                    recommendations.add(book);
                }
            }
        }
        return recommendations;
    }

    public HashSet<Book> otherUserRecommendations(NormalUser currentUser, List<NormalUser> users) {
        ArrayList recommendations = new ArrayList<Book>();
        NormalUser mostSimilarUser = currentUser;
        double minimumDistance = 1.1;

        for (NormalUser user: users) { //Find user with the smallest jaccard distance
            double distance = currentUser.jaccardDistance(user);
            if (distance < minimumDistance && distance > 0) {
                minimumDistance = distance;
                mostSimilarUser = user;
            }
        }
        HashSet exclusive = new HashSet<>(mostSimilarUser.getPurchases());//Remove books already purchased by user
        exclusive.removeAll(currentUser.getPurchases());

        return exclusive;
    }
}

