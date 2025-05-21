package com.vasd.medical_service;

import com.vasd.medical_service.cloudinary.CloudinaryConnectionTest;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedialServicesApplicationTests {

    @Test
    void contextLoads() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        System.out.println("CLOUD_NAME = " + System.getProperty("CLOUD_NAME"));
        System.out.println("CLOUD_API_KEY = " + System.getProperty("CLOUD_API_KEY"));
        System.out.println("CLOUD_API_SECRET = " + System.getProperty("CLOUD_API_SECRET"));


        CloudinaryConnectionTest cloudinaryConnectionTest = new CloudinaryConnectionTest();
        cloudinaryConnectionTest.testCloudinaryApiKeyConnection();
    }

}
