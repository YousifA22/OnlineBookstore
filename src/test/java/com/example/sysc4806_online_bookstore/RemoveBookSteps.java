package com.example.sysc4806_online_bookstore;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class RemoveBookSteps {
    
    @Autowired
    private BookRepo bookRepo;
    private int bookId;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Long bookCount;
    
    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Given("there is a book in the inventory")
    public void givenThereIsABookInInventory() {
        Book book = new Book("Hatchet", "John", "1234-5-6789-2", "Jason", "Sharp!", 12.99,1);
        bookRepo.save(book);
        bookCount = bookRepo.count();
        bookId = book.getId();
    }

    @When("the user selects the delete button")
    public void whenTheUserSelectsDelete() throws Exception {
        mockMvc().perform(MockMvcRequestBuilders.delete("/books/{id}", bookId));
    }

    @Then("the book should be removed from the inventory")
    public void thenTheBookIsRemoved() {
        Assertions.assertEquals(bookCount - 1, bookRepo.count());
    }
}
