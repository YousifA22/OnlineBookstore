package com.example.sysc4806_online_bookstore;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = Sysc4806OnlineBookstoreApplication.class)
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class Cucumber_Config {
}