# Music Manager WebApp

### Business Requirement
_(one liner business requirement)_ I have many-many music albums that I want to **catalog**, **rate** and **generate** different kind of **statistics** about them

### Objectives
1. I want to learn test automation
2. for that I need to learn programming
3. for that I want to write a webapp that I can write automated tests for

## Design the system and choose tools

### Technologies used for the server

- **Kotlin** with Maven
- **GraphQL** for defining the API
- Postman to test tha API
- Some kind of **Document DB** to store data
  - most probably **MongoDB**

### Technologies considered for Frontend
- React SPA (Single Page Application) -> charts

Qery - GET fetch data
Mutation - POST, PUT, PATCH, DELETE
Subscription - Websockets real-time connection

## Main features and relationships

- User <--> Albums - CRUD, filter
- User - create, login
- User - create album, rank album