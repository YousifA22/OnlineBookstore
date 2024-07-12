package com.example.sysc4806_online_bookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookstoreControllerInventoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String URL;
    private ResultActions resultA;

    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("The Inventory URL {string}")
    public void theInventoryURL(String arg0) {
        this.URL = arg0;
    }

    @When("The URL is used to get the Inventory \\(main) page")
    public void theURLIsUsedToGetTheInventoryMainPage() throws Exception {
        resultA = mockMvc().perform(get(URL));
    }

    @Then("Check if the connection with the Inventory \\(main) page is ok")
    public void checkIfTheConnectionWithTheInventoryMainPageIsOk() throws Exception {
        resultA.andDo(print()).andExpect(status().isOk());
    }
}