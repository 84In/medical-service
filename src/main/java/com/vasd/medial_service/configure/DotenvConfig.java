package com.vasd.medial_service.configure;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration class for loading environment variables from a .env file.
 * This class ensures that the .env file is loaded from the project root directory
 * and sets the variables into the system environment for use in the Spring application.
 */
@Configuration
public class DotenvConfig {

    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    /**
     * Loads environment variables from a .env file and sets them into the system environment.
     * The .env file is expected to be located in the project root directory.
     *
     * @throws RuntimeException if the .env file is not found in the specified directory
     */
    public DotenvConfig() {
        String baseDir = System.getProperty("user.dir");
        logger.info("Project root directory: {}", baseDir);
        Path envPath = Path.of(baseDir, ".env");
        logger.info("Looking for .env file at: {}", envPath);
        if (Files.exists(envPath)) {
            Dotenv dotenv = Dotenv.configure()
                    .directory(baseDir)
                    .load();
            logger.info("Loaded .env file successfully");
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.info("Set environment variable: {} = {}", entry.getKey(), entry.getValue());
            });
        } else {
            logger.error("File .env not found at: {}", envPath);
            throw new RuntimeException("File .env not found at: " + envPath);
        }
    }
}
