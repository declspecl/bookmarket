package edu.oakland.sophomoreproject.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.oakland.sophomoreproject")
public class BookmarketApp {
    public static void main(String[] args) {
        SpringApplication.run(BookmarketApp.class, args);
    }
}
