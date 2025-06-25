# Retro Video Game Archive (MVC)

A Spring MVC app for storing information about retro video games.

## Features
- Full CRUD operations on four entities
- Pagination and sorting for each entity
- Error validation (frontend and backend)
- Logging on service operations
- Two profiles: testing (H2 database) and production (PostgreSQL database)
- Spring Security authentication (production profile only)
- Unit and integration testing showcase

## Microservices Architecture
The base app (production profile) can be deployed using a **Minikube-based architecture**.

The architecture was [installed and tested](./k8s-setup.md) in a Windows 11 environment.

## Features
- Microservices configuration for both PostgreSQL (1 replica) and Spring MVC (2 replicas)
- Functional service discovery between database and Spring services
- Horizontal pod autoscaling for the Spring MVC service (up to 3 replicas)
- Metrics monitoring using a Prometheus datasource inside Grafana