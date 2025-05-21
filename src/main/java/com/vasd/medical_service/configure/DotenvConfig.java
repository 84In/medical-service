package com.vasd.medical_service.configure;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration class for loading environment variables from a .env file.
 * This class ensures that the .env file is loaded from the project root directory
 * and sets the variables into the system environment for use in the Spring application.
 *
 * The variables are only loaded if the .env file is present. This allows for safer
 * deployments where .env may not exist (e.g., production environments using native environment variables).
 */
@Configuration
public class DotenvConfig {

    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    /**
     * Loads environment variables from a .env file and sets them into the system environment.
     * The .env file is expected to be located in the project root directory.
     *
     * This method runs automatically after the bean is initialized using @PostConstruct.
     * It avoids throwing exceptions to ensure application startup is not interrupted in non-local environments.
     */
    @PostConstruct
    public void loadEnv() {
        String baseDir = System.getProperty("user.dir");
        logger.info("Project root directory: {}", baseDir);

        Path envPath = Path.of(baseDir, ".env");
        logger.info("Looking for .env file at: {}", envPath);

        if (Files.exists(envPath)) {
            Dotenv dotenv = Dotenv.configure()
                    .directory(baseDir)
                    .ignoreIfMalformed()
                    .ignoreIfMissing()  // Do not fail if file is missing
                    .load();

            logger.info("Loaded .env file successfully");

            // Set each entry as a system property so it can be used by Spring's ${} resolution
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.debug("Set environment variable: {}", entry.getKey()); // Avoid logging secrets
            });

        } else {
            logger.warn("File .env not found at: {}", envPath);
        }
    }
}
