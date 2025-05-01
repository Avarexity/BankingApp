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
``` bash
   mvn clean install
```
### Configuration
No external configuration is required for the in-memory implementation. If you add a database layer later, update the configuration files (e.g., `application.properties` or `persistence.xml`).
### Running the Application
If there is a main class (e.g., `org.bankingapp.App`), run:
``` bash
mvn exec:java -Dexec.mainClass="org.bankingapp.App"
```
Or package and run the JAR:
``` bash
mvn package
java -jar target/bankingapp.jar
```

## Project Structure
``` 
src/
├── main/
│   ├── java/
│   │   └── org.bankingapp/
│   │       ├── model/           # Domain models (Account, Card, OTCard, etc.)
│   │       ├── repository/      # Repository interfaces & in-memory implementations
│   │       ├── service/         # Business logic services
│   │       └── App.java         # (Optional) Main application entry
└── test/
    └── java/
        └── org.bankingapp/
            └── ...              # Unit and integration tests
```

## Usage Examples
Below are simplified snippets to illustrate how you might use the core classes.
``` java
// Create an account with initial balance
Account account = new Account("123-456", BigDecimal.valueOf(1_000));

// Issue a one-time use card
OTCard otCard = new OTCard(
    "4000-1234-5678-9010",
    LocalDate.now().plusYears(1),
    "123",
    account,
    "0000",
    BigDecimal.valueOf(100)
);

// Authorize a payment of $50
boolean success = otCard.authorizePayment(BigDecimal.valueOf(50));
System.out.println("Payment authorized? " + success);  // true
System.out.println("Card used? " + otCard.isUsed());  // true

// A second attempt fails since it's a one-time card
boolean secondAttempt = otCard.authorizePayment(BigDecimal.valueOf(10));
System.out.println("Second attempt? " + secondAttempt);  // false
```

## Testing
Run all unit tests with:
``` bash
mvn test
```

Tests are located under `src/test/java` and use JUnit 5 conventions.

Please adhere to the existing code style and include unit tests for new features.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
