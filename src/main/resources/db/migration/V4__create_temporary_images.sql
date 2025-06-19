CREATE TABLE temporary_images (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  public_id VARCHAR(255) NOT NULL UNIQUE,
                                  url TEXT NOT NULL,
                                  used BOOLEAN DEFAULT FALSE,
                                  uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
