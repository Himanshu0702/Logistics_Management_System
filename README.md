# 🚚 Delivery Logistics Backend (Monolith)

A **Spring Boot–based backend system** for a delivery/logistics platform.  
This project is implemented as a **well-structured monolith**, focusing on **correctness, clean architecture, and proper HTTP semantics**.

---

## ✨ Features

- Spring Boot **4.x**
- Java **21**
- PostgreSQL
- JWT-based **stateless authentication**
- Role-based authorization (**CUSTOMER, AGENT**)
- Event-driven notifications
- Centralized exception handling
- Correct HTTP status codes (400 / 401 / 403 / 404 / 409)

---

## 🧱 Architecture Overview

Controller → Service → Repository
DTO ≠ Entity


### Design Principles
- Thin controllers
- Business logic in services
- Snapshot modeling for orders
- Enum-based state machines
- Security handled at filter level only
- Validation handled at controller level

---

## 📦 Module Structure

com.delivery.logistics
├── auth
├── customer
│ └── address
├── order
├── delivery
├── agent
├── notification
├── common
│ ├── security
│ └── exception


---

## 🔐 Security

### Authentication
- JWT-based (stateless)
- Token contains:
  - `sub` → userId (UUID)
  - `email`
  - `role`

### Authorization
- Role-based access control
- Authorities:

ROLE_CUSTOMER
ROLE_AGENT


### Important Security Rule
> JWT filter handles **authentication only**  
> Business and validation errors are handled by controllers

This prevents security filters from swallowing application exceptions.

---

## 🧠 HTTP Status Code Semantics

| Scenario | HTTP Status |
|--------|-------------|
| Invalid request / validation failure | 400 |
| Resource not found | 404 |
| Conflict (duplicate resource) | 409 |
| Missing or invalid JWT | 401 |
| Valid JWT, wrong role | 403 |

---

## 🌍 Global Exception Handling

All application-level exceptions are handled centrally using `@ControllerAdvice`.

Handled cases include:
- Validation errors (`@Valid`)
- Domain-specific exceptions
- Conflict and not-found scenarios

Example error response:
```json
{
"timestamp": "2026-01-05T12:30:45Z",
"status": 400,
"error": "Bad Request",
"message": "Invalid input data"
}
```

🔔 Notification Module

    Event-driven using ApplicationEventPublisher

    Fully decoupled from business logic

    Triggered on domain events (order confirmed, delivery assigned, etc.)

    Currently logs notifications

    Easily extensible to Email / SMS / Push notifications

▶️ Run Locally
Prerequisites

    Java 21

    PostgreSQL

    Maven

Steps

mvn clean install
mvn spring-boot:run

🛠 Tech Stack

    Java 21

    Spring Boot 4.x

    Spring Security

    Spring Data JPA

    PostgreSQL

    JWT (jjwt)

    Lombok

    Springdoc OpenAPI (Swagger)
