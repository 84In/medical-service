package com.vasd.medical_service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CloudinaryConnectionTest {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Test
    public void testCloudinaryApiKeyConnection() {
        String cloudName = System.getProperty("CLOUD_NAME");
        String apiKey = System.getProperty("CLOUD_API_KEY");
        String apiSecret = System.getProperty("CLOUD_API_SECRET");

        assertNotNull(cloudName, "CLOUD_NAME is not set");
        assertNotNull(apiKey, "CLOUD_API_KEY is not set");
        assertNotNull(apiSecret, "CLOUD_API_SECRET is not set");

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));

        try {
            Map result = cloudinary.api().ping(Collections.emptyMap());

            System.out.println("✅ Cloudinary ping successful: " + result);
            assertEquals("ok", result.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Cloudinary API Key is invalid or request failed: " + e.getMessage());
        }
    }
}
