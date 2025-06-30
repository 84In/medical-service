# Medical Service Backend

This is the backend service for the Medical Service application, which manages healthcare-related data such as doctors, departments, positions, titles, services, and news.

## 📚 Table of Contents

* [Technology Stack](#technology-stack)
* [Features](#features)
* [Getting Started](#getting-started)
* [Database Schema](#database-schema)
* [API Documentation](#api-documentation)
* [Enum Status](#enum-status)
* [Contributing](#contributing)
* [License](#license)

## 🛠 Technology Stack

* Java 17+
* Spring Boot
* JPA/Hibernate
* MySQL
* Lombok
* Swagger/OpenAPI for API documentation

## ✨ Features

* Manage Doctors, Departments, Positions, Titles
* Manage Medical Services and Service Types
* Manage News and News Types
* Role-based User Management
* Enum-based status handling (INACTIVE, ACTIVE, DELETED, HIDDEN)
* CRUD APIs with validation and Swagger documentation
* Unified custom response format with `ApiResponse`

## 🚀 Getting Started

### Prerequisites

* Java 17 or higher
* Maven or Gradle
* MySQL Server

### Setup

1. Clone the repository:

```bash
git clone https://github.com/84In/medical-service.git
cd medical-service
```

2. Configure your database connection in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/medical_service_db
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
```

3. Run database migrations (using Flyway):

Flyway is automatically configured. On app startup, it will auto-run migrations from the `src/main/resources/db/migration` folder.

4. Build and run the application:

```bash
./mvnw spring-boot:run
# or
./gradlew bootRun
```

## 🗃️ Database Schema

The project includes tables for:

* `doctors`, `departments`, `positions`, `titles`
* `services`, `service_types`
* `news`, `news_type`
* `roles`, `users`

Refer to the `/sql` or `src/main/resources/db/migration` folder for detailed schema and migration scripts.

## 📖 API Documentation

Swagger UI is available at:

* [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* or [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Example API Usage

#### Get All Doctors

**Request:**

```http
GET /api/doctors
```

**Custom Response:**

```json
{
  "code": 200,
  "message": "Doctors fetched successfully",
  "results": [
    {
      "id": 1,
      "name": "Dr. Nguyễn Văn A",
      "department": "Cardiology",
      "position": "Head of Department"
    },
    ...
  ]
}
```

> ✅ All APIs return responses using a standardized `ApiResponse<T>` format:
>
> ```java
> public class ApiResponse<T> {
>     private int code;
>     private String message;
>     private T results;
> }
> ```

This improves consistency, error handling, and integration with frontend applications.

## ⚙️ Enum Status

The project uses an enum `Status` to represent entity states:

* `INACTIVE = 0`
* `ACTIVE = 1`
* `DELETED = 2`
* `HIDDEN = 3`

Use this enum consistently across entities.

## 🤝 Contributing

Feel free to open issues or submit pull requests. Contributions are welcome!

## 📄 License

This project is licensed under the **MIT License**.

---

Made with ❤️ by **Nguyễn Trung Tín** – Medical Service Website Project @VASD.
