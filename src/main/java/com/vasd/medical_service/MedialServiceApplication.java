package com.vasd.medical_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedialServiceApplication.class, args);
    }

}
