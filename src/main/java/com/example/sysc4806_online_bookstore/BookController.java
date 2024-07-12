package com.example.sysc4806_online_bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private final BookRepo Repo;
    @Autowired
    public BookController(BookRepo b){
        Repo = b;
    }

    @PostMapping("CreateBook")
    public String CreatedBuddy(@ModelAttribute Book b, Model model){
        Repo.save(b);
        model.addAttribute(Repo);
        return "redirect:/admin";
    }

    @GetMapping("CreateBook")
    public String CreateBuddy(Model m){
        m.addAttribute("Book", new Book());
        return "CreateBook";
    }

    @DeleteMapping("/books/{bookId}")
    public String DeleteBook(@PathVariable Long bookId, Model model) {
        Book book = Repo.findById(bookId);
        Repo.delete(book);
        return "redirect:/admin";
    }
}
