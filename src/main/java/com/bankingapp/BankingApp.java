package com.bankingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class BankingApp {
    public static void main(String[] args) {
        SpringApplication.run(BankingApp.class, args);
    }

    // Basic ping endpoint
    @RestController
    public class PingController {
        @GetMapping("/main-ping")
        public String ping() {
            return "Banking App is alive! Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm:ss")) + " CEST";
        }
    }
}