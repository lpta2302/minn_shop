# 🛍️ Clothing Shop Web Application – Microservices Architecture

This project is a simple web-based clothing shop developed using a **microservices architecture**, powered by the **Spring ecosystem**. The system allows users to **browse**, **purchase**, and **order** clothing products.

---

## ✅ Features

- View product listings
- Add items to cart
- Place orders
- JWT-based authentication
- Secure backend-to-frontend communication with CORS

---

## 🧱 Tech Stack

- **Spring Boot** – Core microservices framework
- **Spring Cloud Gateway** – API Gateway and routing
- **Spring Security** – Authentication & authorization
- **JWT (JSON Web Token)** – Stateless security
- **Kafka** – Asynchronous communication and event-driven messaging
- **Spring Data JPA** – ORM and data persistence
- **Spring WebFlux (Gateway)** – Reactive non-blocking CORS support
- - **Amazon S3** – Images storage

---

## ⚙️ Architecture Overview

The application is composed of several independently deployable microservices:

- `api-gateway`: Acts as the single entry point to the system. Handles routing, request forwarding, CORS, and token validation.
- `config-server`: Centralized configuration management for all microservices using Spring Cloud Config.
- `discovery-server`: A Eureka server that allows microservices to register themselves and discover each other dynamically.
- `auth-service`: Handles authentication and token generation (JWT).
- `product-service`: Manages product catalog.
- `order-service`: Handles user orders.
- `customer-service`: Manages customer accounts and profiles.
- ...

Microservices communicate over **REST APIs** or via **Kafka topics** for async messaging.

---

## 🔐 Authentication

- Uses Spring Security with JWT
- Frontend sends token via `Authorization: Bearer <token>` header
- Gateway validates token and forwards requests to protected services
- CORS properly configured to allow frontend
