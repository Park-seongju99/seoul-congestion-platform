package com.seoul.congestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CongestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(CongestionApplication.class, args);
    }
}
