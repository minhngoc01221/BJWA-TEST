# Salary Management System

Spring Boot employee salary management application for the IASF practical test.

## Features

- Create, read, update, and delete employees.
- Search employees by name, email, position, phone, or department.
- Validate employee data before saving.
- Store data through JPA Hibernate with an H2 demo database.
- Render server-side pages with Thymeleaf, Bootstrap, and jQuery.

## Requirements

- Java 17
- Maven 3.9+ or an IDE with Maven support

## Run

```powershell
mvn spring-boot:run
```

Open:

- Application: `http://localhost:8080/employees`
- H2 console: `http://localhost:8080/h2-console`

H2 settings:

- JDBC URL: `jdbc:h2:mem:salarydb`
- User: `sa`
- Password: leave blank
