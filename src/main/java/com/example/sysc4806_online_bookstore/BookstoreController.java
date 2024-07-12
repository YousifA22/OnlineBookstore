package com.example.sysc4806_online_bookstore;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookstoreController {
    private final BookRepo bookRepo;
    private final NormalUserRepo userRepo;

    private int ID;

    @Autowired
    public BookstoreController(BookRepo bookRepo, NormalUserRepo userRepo){
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String showInventory(Model model){
        model.addAttribute(bookRepo);
        return "showInventory";
    }

    @GetMapping("/admin")
    public String showAdminView(Model model) {
        ArrayList<Book> books = (ArrayList<Book>) bookRepo.findAll();
        model.addAttribute("books", books);
        return "showInventoryAdmin";
    }

    @GetMapping("/user")
    public String showUserView(Model model) {
        model.addAttribute(bookRepo);
        return "showInventoryUser";
    }

    @GetMapping("/getBook")
    public String getBook(@RequestParam("ID") int id, Model model){
        ID = id;
        Book b = bookRepo.findById(id);
        model.addAttribute("Book", b);
        bookRepo.deleteById(id);
        return "getBook";
    }

    @PostMapping("/getBook")
    public String showBook(@ModelAttribute Book b, Model model){
        b.setId(ID);
        bookRepo.save(b);
        model.addAttribute(bookRepo);
        return "redirect:/admin";
    }

    @GetMapping("/Admin")
    public String showInventoryAdmin(Model model){
        //bookRepo.save(new Book("1984", "George Orwell", "123456789", "Secker and Warburg", "Dystopian novel", 8.99));
        model.addAttribute(bookRepo);
        return "redirect:/admin";
    }

    @GetMapping("/User")
    public String showInventoryUser(@RequestParam Integer userId,Model model){
        //bookRepo.save(new Book("1984", "George Orwell", "123456789", "Secker and Warburg", "Dystopian novel", 8.99));
        NormalUser user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        model.addAttribute(user);
        model.addAttribute(bookRepo);
        return "showInventoryUser";
    }


}