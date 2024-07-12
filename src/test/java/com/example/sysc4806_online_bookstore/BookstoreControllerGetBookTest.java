package com.example.sysc4806_online_bookstore;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookstoreControllerGetBookTest {

    @Autowired
    private BookRepo bookRepository;

    private String URL;
    private ResultActions resultA;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("A bookURL {string} for getBook")
    public void aBookURLForGetBook(String arg0) {
        URL = arg0;
    }

    @When("The URL is used to get the Inventory \\(main) page And a Book is in Inventory")
    public void theURLIsUsedToGetTheInventoryMainPageAndABookIsInInventory() throws Exception {

        if(bookRepository.count() == 0) {
            Book book = new Book("1984", "George Orwell", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1);
            bookRepository.save(book);
        }
        resultA = mockMvc().perform(get(URL));
    }

    @Then("Check if the connection and Book is found")
    public void checkIfTheConnectionAndBookIsFound() throws Exception {
        if(resultA != null) {
            resultA.andDo(print()).andExpect(status().isOk());
        }
    }
}