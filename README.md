# Sellio Backend

## Project Description
Sellio Backend is a robust e-commerce platform backend built with Kotlin and Spring Boot. It provides a comprehensive API for managing users, stores, products, orders, and more. The system is designed to support a multi-vendor marketplace environment where users can buy products from various stores, and store owners can manage their inventory and sales.

## Tech Stack
- **Language:** Kotlin (JVM 17)
- **Framework:** Spring Boot 3.5.7
- **Build Tool:** Gradle (Kotlin DSL)
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA (Hibernate)
- **Authentication:** Spring Security with JWT (JSON Web Tokens)
- **Cloud Storage:** AWS S3 (using LocalStack for local development)
- **API Documentation:** OpenAPI (Swagger) via SpringDoc
- **Containerization:** Docker & Docker Compose

## Architecture Overview
The project follows a standard layered architecture to ensure separation of concerns and maintainability:

- **Controller Layer (`api/controller`):** Handles incoming HTTP requests, validates input, and returns responses.
- **Service Layer (`service`):** Contains the core business logic and transaction management.
- **Repository Layer (`repository`):** Interfaces with the database using Spring Data JPA.
- **Entity Layer (`entity`):** Defines the data models mapped to database tables.
- **DTO Layer (`api/dto`):** Data Transfer Objects used for type-safe communication between the client and server.
- **Security Layer (`security`):** Manages authentication (JWT) and authorization.

## Features
- **User Management:**
  - User registration and login (JWT-based).
  - OTP verification for secure actions.
  - Profile management and password resets.
- **Store Management:**
  - Create and manage stores.
  - Store contacts, ratings, and favorites.
  - Store-specific discounts and offers.
- **Product Management:**
  - CRUD operations for products.
  - Support for product variants (Sizes, Colors).
  - Categories and Subcategories hierarchy.
  - Trending products logic.
- **Thrift (Used) Market:**
  - Dedicated module for buying/selling used items.
  - Condition-based filtering and defect reporting.
  - Implemented using **JPA Inheritance (Joined Strategy)** for extensible product types.
- **Custom Designs:**
  - Users can upload custom images to create personalized product designs.
- **Order Processing:**
  - Shopping cart management.
  - Order placement and history.
  - Order item tracking.
- **Search & Discovery:**
  - Search functionality for products and stores.

## Project Structure
```
src/main/kotlin/org/shangahi/sellio_backend
├── api                 # API layer
│   ├── controller      # REST Controllers
│   ├── dto             # Request/Response objects
│   ├── mapper          # Object mappers
│   └── swagger         # OpenAPI configuration
├── config              # General configuration (Storage, etc.)
├── entity              # JPA Entities
├── model               # Domain models and Enums
├── repository          # Data access interfaces
├── security            # Auth logic (JWT, Filters)
└── service             # Business logic
```

## How to Run Locally

### Prerequisites
- Java 17+
- Docker & Docker Compose

### Steps
1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd sellio_backend
   ```

2. **Start Infrastructure:**
   Use Docker Compose to start PostgreSQL and LocalStack (S3 mock).
   ```bash
   docker-compose up -d
   ```

3. **Run the Application:**
   You can run the application using the Gradle wrapper.
   ```bash
   ./gradlew bootRun
   ```
   The application will start on port `8085` (as defined in `application-dev.properties`).

## Environment Variables
The application uses `application.properties` and `application-dev.properties`. Key configuration can be overridden via environment variables, especially when running in Docker.

| Variable | Description | Default (Dev) |
|----------|-------------|---------------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:postgresql://localhost:5432/sellio_db` |
| `SPRING_DATASOURCE_USERNAME` | Database User | `sellio_user` |
| `SPRING_DATASOURCE_PASSWORD` | Database Password | `sellio_dev_pass` |
| `JWT_SECRET` | Secret key for signing JWTs | (your_very_long_secret_key_base64...) |
| `STORAGE_BUCKET` | S3 Bucket name | `bucket name` |
| `STORAGE_ENDPOINT` | S3 Endpoint URL | `http://localhost:4566` |

## API Documentation
Once the application is running, you can access the interactive API documentation (Swagger UI) at:
```
http://localhost:8085/swagger-ui.html
```
This interface allows you to explore endpoints, see request/response schemas, and test APIs directly.

## Authentication Flow
1. **Register:** Create a new account via `/api/v1/auth/register` (or similar endpoint).
2. **Login:** Authenticate via `/api/v1/auth/login` to receive a `Bearer` token.
3. **Access Protected Routes:** Include the token in the `Authorization` header of subsequent requests:
   ```
   Authorization: Bearer <your_jwt_token>
   ```
4. **OTP:** Certain actions like registration or password reset may require OTP verification sent via SMS (mocked in dev).

## Database Schema Overview
Key entities in the system include:
- **User:** Represents platform users (buyers/sellers).
- **Store:** Represents a shop owned by a user.
- **Product:** Items listed for sale, linked to a Store and SubCategory.
- **ProductItem:** Specific variants of a product (e.g., specific size/color stock).
- **Order:** Represents a purchase transaction.
- **Cart:** Temporary storage for items before purchase.
- **Category/SubCategory:** Hierarchical organization of products.
- **Offer/Discount:** Promotional logic.
