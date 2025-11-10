# Dynamic Filtering

A Java project demonstrating advanced filtering capabilities for JPA entities using both **JPA Criteria API** or **QueryDSL**.

## Installation & Running

```bash
# Clone and build
git clone https://github.com/dennismuehlegger/dynamic-filtering.git
cd dynamic-filtering
mvn clean install
```

## Features

- **Dual Implementation Approach**
  - JPA Criteria API
  - QueryDSL
- **Dynamic Operator Support** (equals, greater than, less than, like, etc.)
- **Join Support** (left joins, nested joins)
- **Comprehensive Test Coverage**

## Technologies

- **Java 21** - Core language
- **JUnit 4** - Unit and integration testing
- **Maven** - Build and dependency management
- **JPA/Hibernate** - Object-relational mapping for database interactions
- **GitHub Actions** - Automated CI/CD pipeline for building and testing

## Entity Model

The project includes sample entities representing a simplified order management system:
- `Kunde` (Customer)
- `Bestellung` (Order)
- `Produkt` (Product)
- `Ort` / `Landkreis` / `Bundesland` (Location hierarchy)
See `src/main/resources/ERModell.png` for the entity relationship diagram.

**Note**: Database configuration is currently handled via `persistence.xml` in test resources. For production use, configure your database connection accordingly.
