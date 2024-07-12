package com.example.sysc4806_online_bookstore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NormalUserRepoTest {

    @Autowired
    private NormalUserRepo userRepository;
    private NormalUser savedUser;

    @Given("a user with name {string} and userID {double} and password {string}")
    public void a_user_with_details(String name, double userID,String passWord) {
        NormalUser user = new NormalUser("chris",12,passWord);
        savedUser = userRepository.save(user);
    }

    @When("the user is saved in the repository")
    public void the_user_is_saved_in_the_repository() {
        assertNotNull(savedUser);
    }

    @Then("the user should be retrievable with the same details")
    public void the_user_should_be_retrievable_with_the_same_details() {
        NormalUser foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getUserID(), foundUser.getUserID());
    }
}
