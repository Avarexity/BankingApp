# BankingApp
A simple, modular Java-based banking application that demonstrates core banking operations such as account management, card issuance (including one-time use cards), and transaction processing. The project is organized into clearly separated layers—model, repository, service, and (optionally) controller—to showcase best practices in object-oriented design and clean architecture.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
    - [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Usage Examples](#usage-examples)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features
- Create and manage multiple bank **Accounts**
- Issue different types of **Cards**
    - **DebitCard**
    - **CreditCard**
    - **OTCard** (One-Time Use Card)

- Perform **transactions** with proper authorization and limits
- Plug-and-play persistence (in-memory repository by default)
- Easily extensible to add new card types, services, or a web/API layer

## Architecture
The project follows a layered architecture:
1. **Model**: Plain Java objects (`Account`, `Card`, , etc.) `OTCard`
2. **Repository**: Interfaces and in-memory implementations to manage data persistence
3. **Service**: Business logic (e.g., `AccountService`, `CardService`, `TransactionService`)
4. **Controller** (optional): Entry points for user interaction or REST API
5. **Util**: Common utilities and helpers

## Tech Stack
- Java SE 23
- Build Tool: **Maven** (you can adapt to Gradle if preferred)
- Testing Framework: JUnit 5

## Getting Started
### Prerequisites
- Java 24 (JDK 24)
- Maven 3.6+ (if using Maven)

### Installation
1. Clone the repository
``` bash
   git clone https://github.com/yourusername/BankingApp.git
   cd BankingApp
```

2. Build the project
```bash
  mvn clean install
``` 

### Configuration
The application uses Spring configuration. Modify `application.properties` or `application.yml` to configure:
- Database connection settings
- Logging levels
- Application-specific properties

### Running the Application
Execute the main application class:
```
bash mvn spring-boot:run
``` 

Or build and run the JAR:
```
bash mvn package java -jar target/bankingapp.jar
``` 

## Project Structure

```
src/
├── main/ 
│   ├── java/
│   │    └── com.bankingapp/
│   │         ├── model/ # Domain entities
│   │         ├── repository/ # Spring Data repositories
│   │         ├── service/ # Business logic services
│   │         ├── controller/ # REST controllers
│   │         ├── util/ # Utilities and helpers
│   │         └── BankingApplication.java # Main application entry
│   └── resources/
│            └── application.properties # Configuration
└── test
    └── java
         └── com.bankingapp/
             └── ... # Unit and integration tests
```

## Usage Examples

### Creating an Account and Card
```java
// Create a new account
Account account = new Account("123-456", BigDecimal.valueOf(1000)); accountRepository.save(account);
// Issue a one-time use card
OTCard otCard = new OTCard( ; cardRepository.save(otCard);
// Process a transaction
boolean success = otCard.authorizePayment(BigDecimal.valueOf(50)); System.out.println("Payment authorized? " + success); // true System.out.println("Card used? " + otCard.isUsed()); // true
// Second attempt fails since it's a one-time card
boolean secondAttempt = otCard.authorizePayment(BigDecimal.valueOf(10));
System.out.println("Second attempt? " + secondAttempt); // false
``` 

### RESTful API Examples
```
# Create a new account
POST /api/accounts Content-Type: application/json
{ "accountNumber": "123-456", "balance": 1000 }
# Issue a card for an account
POST /api/cards Content-Type: application/json
{ "cardNumber": "4000-1234-5678-9010", "expiryDate": "2025-06-30", "cvv": "123", "accountId": 1, "pin": "0000", "cardType": "OT", "limit": 100 }
``` 

## Testing
Run all tests with:
```bash
  mvn test
``` 

The project includes unit tests for all layers and integration tests for API endpoints.

## Contributing
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file.

