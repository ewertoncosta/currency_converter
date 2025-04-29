This is a **Currency Converter** application built using **Kotlin**, **Spring Boot**, and **Maven**. It provides functionality to convert currencies using exchange rates fetched from an external API.

## Features
- Convert currencies between different currency codes.
- Validate currency codes and amounts.
- Store transaction details in a database.
- Secure user authentication and transaction tracking.

## Prerequisites
Before running the application locally, ensure you have the following installed:
- **Java 17** or higher
- **Maven 3.8** or higher
- **Kotlin 1.8** or higher
- **PostgreSQL** (or any other database supported by Spring Boot)
- **Git**

## Running the Application Locally

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/currency-converter.git
cd currency-converter
```

This is a **Currency Converter** application built using **Kotlin**, **Spring Boot**, and **Maven**. It provides functionality to convert currencies using exchange rates fetched from an external API.

## Features
- Convert currencies between different currency codes.
- Validate currency codes and amounts.
- Store transaction details in a database.
- Secure user authentication and transaction tracking.

## Prerequisites
Before running the application locally, ensure you have the following installed:
- **Java 17** or higher
- **Maven 3.8** or higher
- **Kotlin 1.8** or higher
- **Git**

## Running the Application Locally

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/currency-converter.git
cd currency-converter
```

### 2. Configure the Database
The **Users** table is created automatically when the application starts. The application uses an embedded **H2 database** for local development. No additional setup is required. The database configuration is already provided in `src/main/resources/application.yml`:
  
```yaml
spring:
  application:
    name: currency-converter
  datasource:
    url: 'jdbc:h2:mem:testdb'
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
```

### 3. Build the Application
Run the following command to build the application:
```bash
mvn clean install
```

### 4. Run the Application
Start the application using:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 5. Test the Application
You can test the application using tools like **Postman** or **cURL**. Example endpoints:
- `GET /exchange` - Convert currencies.
- `GET /exchange/history` - Retrieve transaction history.

### 6. Running Tests
To run the test suite, execute:
```bash
mvn test
```
## API Documentation
The application uses **Springdoc OpenAPI** for API documentation. Once the application is running, access the API docs at:
```
http://localhost:8080/swagger-ui.html
```

## License
This project is licensed under the MIT License.