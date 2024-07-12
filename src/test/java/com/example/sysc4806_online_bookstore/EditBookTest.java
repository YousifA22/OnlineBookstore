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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class EditBookTest {

    @Autowired
    private BookRepo bookRepository;
    private ResultActions resultA;

    private Book b;
    private Book newb;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("A Book is added to the BookStore")
    public void aBookIsAddedToTheBookStore() {
        b = new Book();
        b.setId(1);
        bookRepository.save(b);
    }

    @When("The EditBook EditURL is used {string}")
    public void theEditBookEditURLIsUsed(String arg0) throws Exception {
        resultA = mockMvc().perform(get(arg0));
        resultA = mockMvc().perform(post("/getBook"));
        if(!bookRepository.findAll().isEmpty()) {
            newb = bookRepository.findAll().get(0);
        }
    }

    @Then("Check if the connection with the Inventory \\(main) page is ok and updated inventory")
    public void checkIfTheConnectionWithTheInventoryMainPageIsOkAndUpdatedInventory() throws Exception {
        if(resultA != null && b != newb) {
            resultA.andDo(print()).andExpect(status().is3xxRedirection());
        }
    }
}
