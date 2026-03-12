# Tote Staging API
A Spring Boot API for managing **Totes** and their **items** in a staging environment, designed for order fulfillment workflows. The API supports creating totes, adding items, and managing temperature-sensitive products efficiently.
---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)

---
## Features

- Create, update, and manager Totes
- Add, remove, and track ToteItems
- Enforce **temperature bands** for consolidated Totes
- Automatic timestamp tracking (`createdAt`) for Totes
- Full JPA integration with PostgreSQL

---

## Technologies

- **Spring Boot** - API Framework
- **Spring Data JPA** - Database ORM
- **PostgreSQL** - Relational Database
- **Maven** - Project build tool
- **JUnit 5 & Mockito** - Unit testing
- **Lombok** - Reduces boilerplate code

---

## Getting Started

### Prerequisites

- Java 17+
- PostgreSQL 14+
- Maven 3+

### Clone the Repository

```bash
git clone https://github.com/redealexander9/totemanagementapi
cd totemanagementapi
