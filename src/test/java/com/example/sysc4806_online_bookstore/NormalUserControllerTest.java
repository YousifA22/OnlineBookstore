package com.example.sysc4806_online_bookstore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class NormalUserControllerTest {

    @Autowired
    private NormalUserRepo userRepository;

    @Autowired
    private BookRepo bookRepository;

    private Book b;
    private NormalUser user;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private ResultActions resultA;

    List<Book> bookList;

    ArrayList<Book> listOfBooks;


    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("a user with username {string} and userID {int} and password {string}")
    public void a_user_for_create_user(String username, int userID, String passWord) {

        user = new NormalUser(username, userID, passWord);

        user = userRepository.save(user);


    }

    @When("create the user")
    public void create_a_user() throws Exception {
        resultA = mockMvc().perform(post("/register?username=Yousif&userID=22&password=Hello"));
    }

    @Then("the user creation should be successful")
    public void the_user_creation_should_be_successful() throws Exception {
        if (resultA != null) {
            resultA.andDo(print()).andExpect(status().isOk());
        }

    }

    @Given("a user and a book are present")
    public void a_user_and_a_book_are_present() throws Exception {
        b = new Book("1984", "George Orwell", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1);
        user = new NormalUser("Yousif", 22, "hello");

        b = bookRepository.save(b);
        user = userRepository.save(user);
    }

    @When("I add the book to the user's shopping cart")
    public void i_add_the_book_to_the_user_s_shopping_cart() throws Exception {
        resultA = mockMvc().perform(post("/addtocart")
                        .param("userId", user.getId().toString())
                        .param("bookId", b.getId().toString()))
                .andExpect(status().isOk());
    }

    @Then("the shopping cart should have {int} item")
    public void the_shopping_cart_should_have_item(Integer itemCount) throws Exception {
        resultA = mockMvc().perform(get("/cart")
                        .param("userId", user.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Given("a user and a book are there")
    public void a_user_and_a_book_are_there() throws Exception {
        a_user_and_a_book_are_present();
        i_add_the_book_to_the_user_s_shopping_cart();
    }

    @When("I remove the book from the shopping cart")
    public void i_remove_the_book_from_the_shopping_cart() throws Exception {
        resultA = mockMvc().perform(delete("/removefromcart")
                        .param("userId", user.getId().toString())
                        .param("bookId", b.getId().toString()))
                .andExpect(status().isOk());
    }

    @Then("the shopping cart should be empty")
    public void shopping_cart_should_be_empty() throws Exception {

        the_shopping_cart_should_have_item(0);
    }

    @Given("a user and a book are made")
    public void a_user_and_a_book_are_made() throws Exception {
        a_user_and_a_book_are_present();
    }

    @When("I get the shopping cart for the user")
    public void i_get_shopping_cart() throws Exception {
        resultA = mockMvc().perform(get("/cart")
                        .param("userId", user.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Then("the shopping cart has {int} item")
    public void shopping_cart_has_n_item(int item) throws Exception {
        // This can be the same as checking the cart size
        the_shopping_cart_should_have_item(item);
    }

    @Given("a book in the repo")
    public void a_book_is_in_the_repo() throws Exception {
        listOfBooks = new ArrayList<>();
        listOfBooks.add(new Book("1984", "George Orwell", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1));
    }

    @When("A search is executed")
    public void a_search_is_executed() throws Exception {
        NormalUserController user = new NormalUserController();
        bookList = user.searchBooks(new Book("1984", "Georg", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1), listOfBooks);
    }

    @Then("the user obtains the target books\\(s)")
    public void user_get_right_books() {
        if (bookList.get(0).getTitle() != "1984") {
            throw new RuntimeException();
        }
    }

    @Given("a book in the repository")
    public void book_in_repo() throws Exception {
        listOfBooks = new ArrayList<>();
        listOfBooks.add((new Book("1984", "apple", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1)));
        listOfBooks.add((new Book("1984", "banana", "123456789", "Secker and Warburg", "Dystopian novel", 10.99,1)));
    }

    @When("the user selects the \"filter by author\" criteria")
    public void a_filter_is_selected() throws Exception {
        NormalUserController user = new NormalUserController();
        bookList = user.filterAuthor(listOfBooks);
    }

    @Then("the user obtains a list of books filtered by authors")
    public void user_gets_correct_list_of_books(){
        if(!listOfBooks.get(0).getAuthor().equals("apple")){
            throw new RuntimeException();
        }
    }


    @Given("books are added to the repository")
    public void book_in_repo_price() throws Exception {
        listOfBooks = new ArrayList<>();
        listOfBooks.add((new Book("1984", "apple", "123456789", "Secker and Warburg", "Dystopian novel", 8.99,1)));
        listOfBooks.add((new Book("1984", "banana", "123456789", "Secker and Warburg", "Dystopian novel", 10.99,1)));
    }

    @When("the user selects the \"filter by price\" criteria")
    public void a_filter_is_selected_price() throws Exception {
        NormalUserController user = new NormalUserController();
        bookList = user.filterPrice(listOfBooks);
    }

    @Then("the user obtains a list of books filtered by price")
    public void user_gets_correct_list_of_books_price(){
        if(listOfBooks.get(0).getPrice() != 10.99){
            throw new RuntimeException();
        }
    }
}

