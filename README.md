# Spring Boot Book Management System

This repo tracks my 7-day journey learning Spring Boot as part of an internship training. I started with a basic CRUD app and kept building on it day after day and by the end it had grown into a full system with JWT auth, caching, real-time messaging, and Docker support.

Each `dayN` folder is a complete, working snapshot from that point in the project, so you can open any of them and run it on its own.

## Tech Stack

Java 21, Spring Boot 4.1.0, Spring Data JPA + Hibernate + MySQL, Spring Security with JWT, JUnit 5 + Mockito, Caffeine caching, Apache Kafka, Docker, WebSockets (STOMP), Swagger, Spring Boot Actuator.

## Folder Contents

**Day 2 — "day2/bookmanagement"
Basic CRUD, split into Controller/Service/Repository layers, with DTOs and logging.

**Day 3 — "day3/day3-jpa-validation"
Added real entity relationships between Book and Author, custom queries, pagination, proper exception handling, and JWT-based login with role-based permissions.

**Day 4 — "day4/day4task"
Unit and integration tests, Swagger documentation, environment profiles (dev/staging/prod), and OAuth2 login via Firebase.

**Day 5 — "day5/bookmanagement_day5"
Caching with Caffeine, application events, server-sent events for streaming, and a comparison of RestTemplate vs WebClient.

**Day 6 — "day6/bookmanagement_day6"
Real-time features with WebSockets — live comments between users and instant notifications when a new book gets added.

**Day 7 — "day7/bookmanagement_day7"
Dockerized the app, set up a GitHub Actions workflow, added health/metrics monitoring with Actuator, and hooked in Kafka for event-driven messaging.
