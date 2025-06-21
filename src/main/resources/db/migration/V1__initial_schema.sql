-- Tạo bảng Titles
CREATE TABLE titles (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL UNIQUE,
                        description TEXT,
                        status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Positions
CREATE TABLE positions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           description TEXT,
                           status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Departments
CREATE TABLE departments (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             content_html TEXT,
                             status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Doctors
CREATE TABLE doctors (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         avatar_url VARCHAR(255),
                         introduction TEXT,
                         experience_years INT CHECK (experience_years >= 0),
                         department_id INT,
                         position_id INT,
                         title_id INT,
                         status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3),
                         FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL,
                         FOREIGN KEY (position_id) REFERENCES positions(id) ON DELETE SET NULL,
                         FOREIGN KEY (title_id) REFERENCES titles(id) ON DELETE SET NULL
);

-- Tạo bảng News_Type
CREATE TABLE news_type (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng news
CREATE TABLE news (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      slug VARCHAR(100) NOT NULL UNIQUE,
                      name VARCHAR(100) NOT NULL,
                      thumbnail_url VARCHAR(255),
                      description_short TEXT,
                      content_html TEXT,
                      status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      news_type_id INT,
                      FOREIGN KEY (news_type_id) REFERENCES news_type(id) ON DELETE SET NULL
);

-- Tạo bảng ServiceTypes
CREATE TABLE service_types (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(100) NOT NULL UNIQUE,
                               status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3),
                               description TEXT
);

-- Tạo bảng Services
CREATE TABLE services (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          slug VARCHAR(100) NOT NULL UNIQUE,
                          name VARCHAR(100) NOT NULL,
                          thumbnail_url VARCHAR(255),
                          description_short TEXT,
                          content_html TEXT,
                          status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK (status BETWEEN 0 AND 3),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          serviceType_id INT,
                          FOREIGN KEY (serviceType_id) REFERENCES service_types(id) ON DELETE SET NULL
);

-- Tạo bảng Roles
CREATE TABLE roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Tạo bảng Users
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       email VARCHAR(100) UNIQUE,
                       role_id INT,
                       FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL
);
