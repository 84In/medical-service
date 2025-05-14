-- Tạo bảng Titles
CREATE TABLE titles (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        description TEXT,
                        status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Positions
CREATE TABLE positions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Departments
CREATE TABLE departments (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(100) NOT NULL,
                             description TEXT,
                             status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Doctors
CREATE TABLE doctors (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         avatarUrl VARCHAR(255),
                         introduction TEXT,
                         experience_years INT,
                         department_id INT,
                         position_id INT,
                         title_id INT,
                         status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                         FOREIGN KEY (department_id) REFERENCES departments(id),
                         FOREIGN KEY (position_id) REFERENCES positions(id),
                         FOREIGN KEY (title_id) REFERENCES titles(id)
);

-- Tạo bảng News_Type
CREATE TABLE news_type (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng news
CREATE TABLE news (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      slug VARCHAR(100) NOT NULL,
                      name VARCHAR(100) NOT NULL,
                      thumbnailUrl VARCHAR(255),
                      descriptionShort TEXT,
                      contentHtml TEXT,
                      status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                      createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      news_type_id INT,
                      FOREIGN KEY (news_type_id) REFERENCES news_type(id)
);

-- Tạo bảng ServiceTypes
CREATE TABLE service_types (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL,
                              status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                              description TEXT
);

-- Tạo bảng MediaServices
CREATE TABLE services (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               slug VARCHAR(100) NOT NULL,
                               name VARCHAR(100) NOT NULL,
                               thumbnailUrl VARCHAR(255),
                               descriptionShort TEXT,
                               contentHtml TEXT,
                               status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                               createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                               updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               serviceType_id INT,
                               FOREIGN KEY (serviceType_id) REFERENCES service_types(id)
);

-- Tạo bảng Roles
CREATE TABLE roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL
);

-- Tạo bảng Users
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       email VARCHAR(100) UNIQUE,
                       role_id INT,
                       FOREIGN KEY (role_id) REFERENCES roles(id),
                       CONSTRAINT uk_username UNIQUE (username)
);
