# Coupon Manager API

A REST API service for managing coupons.  
Built with **Kotlin + Spring Boot + PostgreSQL + Flyway**.

The system supports:
- Creating coupons
- Using coupons with business constraints (country, usage limits, per-user restriction)
- Concurrency-safe coupon usage (pessimistic locking)

---

## Tech Stack

- Kotlin
- Spring Boot (Web, Data JPA)
- PostgreSQL
- Flyway (DB migrations)
- Testcontainers (integration tests)
- MockK (unit/integration mocking)
- Gradle Kotlin DSL

---

## Requirements

- Java 21
- Docker
- Gradle

---

## How to run the application

### 1. Start PostgreSQL (Docker)

```bash
docker compose up -d
```

### 2. Run application

```bash
./gradlew bootRun
```
application runs on http://localhost:8080

### 3. Run tests

```bash
./gradlew test
```

## Sample requests

### 1. Create coupon
```bash
POST /coupons
Content-Type: application/json

{
  "code": "SUMMER2026",
  "maxUsages": 10,
  "countryCode": "PL"
}
```


### 2. Use coupon
```bash
POST /coupons/use
Content-Type: application/json
X-Forwarded-For: 37.31.55.0

{
  "code": "SUMMER2026",
  "userId": "8be4df61-93ca-11d2-aa0d-00e098032b87"
}
```

