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

- Create, update, and manage Totes
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
- PostgreSQL 18+
- Maven 3.9.12 (included via Maven Wrapper)

### Clone the Repository

```bash
git clone https://github.com/redealexander9/totemanagementapi
cd totemanagementapi
```
### Configure Database

Update `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tote_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Create the database manually (if not using `ddl-auto=update`):

```sql
CREATE DATABASE tote_db;
```
# Run the Application

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

# Database Schema

## Tote

| Column                | Type      |
|-----------------------|-----------|
| id                    | BIGINT PK |
| is_fragile            | VARCHAR   |
| location              | VARCHAR   |
| order_number          | VARCHAR   |
| sequence_number       | VARCHAR   |
| status                | VARCHAR   |
| temp                  | VARCHAR   |
| pick_walk_finished_at | TIMESTAMP |
| tote_created_time     | TIMESTAMP |
| first_item_picked_at  | TIMESTAMP |
| pick_walk_due_at      | TIMESTAMP |
| shopper_ids           | VARCHAR[] |
| batch_id              | VARCHAR   |
| type                  | SMALLINT  |

## ToteItem

| Column           | Type      |
|------------------|-----------|
| id               | BIGINT PK |
| product_id       | BIGINT FK |
| tote_id          | BIGINT FK |
| quantity_ordered | integer   |
| quantity_picked  | integer   |

## Product

| Column         | Type       |
|----------------|------------|
| upc            | VARCHAR PK |
| num_facings    | INTEGER    |
| position       | INTEGER    |
| section        | INTEGER    |
| shelf_capacity | INTEGER    |
| temp_band      | SMALLINT   |
| weight         | DOUBLE     |
| aisle          | VARCHAR    |
| name           | VARCHAR    |


# API Endpoints

## Tote Endpoints
| Method | Endpoint                                   | Description                         |
|--------|--------------------------------------------|-------------------------------------|
| POST   | `/totes`                                   | Create Tote                         |
| POST   | `/totes/{id}/addItem`                      | Add Item to Tote                    |
| POST   | `/totes/{id}/stage`                        | Stage Tote to Location              |
| POST   | `/totes/{targetId}/consolidate/{sourceId}` | Consolidate totes                   |
| GET    | `/totes`                                   | Get All Totes                       |
| GET    | `/totes/{id}`                              | Get Tote by id                      |
| GET    | `/totes/{id}/items`                        | Get items in Tote                   |
| GET    | `/totes/unstaged`                          | Get all Unstaged Totes              |
| GET    | `/totes/unstaged/{shopperId}`              | Get all Unstaged Totes by shopperId |
| PATCH  | `/totes/{id}/editTote`                     | Edit Tote                           |
| DELETE | `/totes/{toteId}`                          | Delete Tote                         |

## ToteItem Endpoints
| Method | Endpoint      | Description           |
|--------|---------------|-----------------------|
| POST   | `/items`      | Create ToteItem       |
| GET    | `/items`      | Get all ToteItems     |
| GET    | `/items/{id}` | Get ToteItem by id    |
| DELETE | `/items/{id}` | Delete ToteItem by id |

## Product Endpoints
| Method | Endpoint                  | Description             |
|--------|---------------------------|-------------------------|
| POST   | `/products`               | Create Product          |
| GET    | `/products`               | Get all Products        |
| GET    | `/products/{id}`          | Get Product by id       |
| GET    | `/products/{id}/location` | Get Location of Product |