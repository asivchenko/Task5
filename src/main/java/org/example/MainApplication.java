package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        System.out.printf("Hello Spring Boot 26_06_2025!");
        SpringApplication.run(MainApplication.class,args);
    }
}

