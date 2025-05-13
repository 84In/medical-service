-- Tạo bảng Titles
CREATE TABLE Titles (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        description TEXT,
                        status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Positions
CREATE TABLE Positions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Departments
CREATE TABLE Departments (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(100) NOT NULL,
                             description TEXT,
                             status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3)
);

-- Tạo bảng Doctors
CREATE TABLE Doctors (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         avatarUrl VARCHAR(255),
                         introduction TEXT,
                         experience_years INT,
                         department_id INT,
                         position_id INT,
                         title_id INT,
                         status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                         FOREIGN KEY (department_id) REFERENCES Departments(id),
                         FOREIGN KEY (position_id) REFERENCES Positions(id),
                         FOREIGN KEY (title_id) REFERENCES Titles(id)
);

-- Tạo bảng Shifts
CREATE TABLE Shifts (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                        start_time TIME NOT NULL,
                        end_time TIME NOT NULL
);

-- Tạo bảng Doctor_Schedules
CREATE TABLE Doctor_Schedules (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  date DATE NOT NULL,
                                  doctor_id INT,
                                  shift_id INT,
                                  status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                                  FOREIGN KEY (doctor_id) REFERENCES Doctors(id),
                                  FOREIGN KEY (shift_id) REFERENCES Shifts(id)
);

-- Tạo bảng ServiceTypes
CREATE TABLE ServiceTypes (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL,
                              status TINYINT UNSIGNED CHECK (status BETWEEN 0 AND 3),
                              description TEXT
);

-- Tạo bảng MediaServices
CREATE TABLE MediaServices (
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
                               FOREIGN KEY (serviceType_id) REFERENCES ServiceTypes(id)
);

-- Tạo bảng Roles
CREATE TABLE Roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL
);

-- Tạo bảng Users
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       email VARCHAR(100) UNIQUE,
                       role_id INT,
                       FOREIGN KEY (role_id) REFERENCES Roles(id),
                       CONSTRAINT uk_username UNIQUE (username)
);
