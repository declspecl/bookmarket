package edu.oakland.sophomoreproject.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "edu.oakland.sophomoreproject")
public class BookmarketApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BookmarketApp.class, args);
    }
}
