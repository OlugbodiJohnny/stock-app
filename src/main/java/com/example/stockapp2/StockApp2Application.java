package com.example.stockapp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockApp2Application {

    public static void main(String[] args) {
        SpringApplication.run(StockApp2Application.class, args);
    }

}
