# ABC Telecom Postpaid Billing System

## Overview
The ABC Telecom Postpaid Billing System is a secure, modular, enterprise backend application built using Spring Boot. It provides a comprehensive solution for managing postpaid billing operations, including user authentication, customer management, account handling, billing processes, and invoice generation.

## Features
- **JWT-based Authentication**: Secure user authentication using JSON Web Tokens (JWT).
- **Modular Architecture**: Organized into distinct modules for controllers, services, repositories, and entities.
- **Complete CRUD Operations**: Full Create, Read, Update, and Delete functionality for users, customers, accounts, and invoices.
- **API Documentation**: Automatically generated API documentation using Swagger.
- **Exception Handling**: Global exception handling to manage errors gracefully.
- **Database Migration**: Initialization and migration scripts for setting up the H2 database.

## Project Structure
```
abc-telecom-postpaid-billing
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── abc
│   │   │           └── telecom
│   │   │               └── billing
│   │   │                   ├── AbcTelecomBillingApplication.java
│   │   │                   ├── config
│   │   │                   │   ├── SwaggerConfig.java
│   │   │                   │   └── ModelMapperConfig.java
│   │   │                   ├── controller
│   │   │                   │   ├── AuthController.java
│   │   │                   │   ├── UserController.java
│   │   │                   │   ├── CustomerController.java
│   │   │                   │   ├── AccountController.java
│   │   │                   │   ├── BillingController.java
│   │   │                   │   └── InvoiceController.java
│   │   │                   ├── dto
│   │   │                   │   ├── auth
│   │   │                   │   │   ├── LoginRequest.java
│   │   │                   │   │   └── JwtResponse.java
│   │   │                   │   └── billing
│   │   │                   │       └── InvoiceDto.java
│   │   │                   ├── entity
│   │   │                   │   ├── User.java
│   │   │                   │   ├── Role.java
│   │   │                   │   ├── Customer.java
│   │   │                   │   ├── PostpaidAccount.java
│   │   │                   │   ├── Invoice.java
│   │   │                   │   └── Payment.java
│   │   │                   ├── repository
│   │   │                   │   ├── UserRepository.java
│   │   │                   │   ├── CustomerRepository.java
│   │   │                   │   ├── AccountRepository.java
│   │   │                   │   └── InvoiceRepository.java
│   │   │                   ├── service
│   │   │                   │   ├── AuthService.java
│   │   │                   │   ├── UserService.java
│   │   │                   │   ├── CustomerService.java
│   │   │                   │   ├── BillingService.java
│   │   │                   │   └── impl
│   │   │                   │       └── (implementations).java
│   │   │                   ├── security
│   │   │                   │   ├── JwtTokenProvider.java
│   │   │                   │   ├── JwtAuthenticationFilter.java
│   │   │                   │   ├── SecurityConfig.java
│   │   │                   │   └── CustomUserDetailsService.java
│   │   │                   ├── exception
│   │   │                   │   ├── ApiException.java
│   │   │                   │   └── GlobalExceptionHandler.java
│   │   │                   ├── util
│   │   │                   │   └── (utilities).java
│   │   │                   └── mapper
│   │   │                       └── (mappers).java
│   │   └── resources
│   │       ├── db
│   │       │   └── migration
│   │       │       └── V1__init.sql
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   └── test
│       └── java
│           └── com
│               └── abc
│                   └── telecom
│                       └── billing
│                           ├── controller
│                           │   └── (controller tests).java
│                           └── service
│                               └── (service tests).java
├── docs
│   ├── architecture.md
│   ├── api-spec.yaml
│   ├── security.md
│   └── deployment.md
├── .gitignore
├── mvnw
├── mvnw.cmd
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Getting Started
1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd abc-telecom-postpaid-billing
   ```

2. **Build the Project**: 
   ```
   ./mvnw clean install
   ```

3. **Run the Application**: 
   ```
   ./mvnw spring-boot:run
   ```

4. **Access the API**: The API will be available at `http://localhost:8080`.

## Documentation
- API documentation is available at `/swagger-ui.html` after running the application.
- For detailed architecture, security measures, and deployment instructions, refer to the `docs` directory.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.